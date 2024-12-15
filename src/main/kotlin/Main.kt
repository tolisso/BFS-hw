package io.github.tolisso

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicIntegerArray

fun seq(graph: List<List<Int>>): List<Int> {
    val ans = MutableList (graph.size) { -1 }
    val queue = ArrayDeque<Int>()
    queue.addFirst(0)
    ans[0] = 0

    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        for (to in graph[node]) {
            if (ans[to] == -1) {
                ans[to] = ans[node] + 1
                queue.addLast(to)
            }
        }
    }

    return ans
}

const val PARALLELISM_BORDER = 10000

fun par(graph: List<List<Int>>) = Executors.newFixedThreadPool(4).asCoroutineDispatcher().use { dispatcher ->
    runBlocking(dispatcher) {
        val ans = AtomicIntegerArray(IntArray(graph.size) { -1 })
        var depth = 0
        var f = listOf(0)
        ans.set(0, 0)

        while (f.isNotEmpty()) {
            val borders = (0 until f.size / PARALLELISM_BORDER).map { it to it + PARALLELISM_BORDER } +
                    (f.size - f.size % PARALLELISM_BORDER to f.size)
            f = borders.map { (fromF, toF) ->
                async {
                    val next = mutableListOf<Int>()
                    (fromF until toF).map { index ->
                        val from = f[index]
                        for (to in graph[from]) {
                            if (ans.compareAndSet(to, -1, depth + 1)) {
                                next.add(to)
                            }
                        }
                    }
                    next
                }
            }.toList().awaitAll().flatten()

            depth++
        }

        List(graph.size) { ans[it] }
    }
}