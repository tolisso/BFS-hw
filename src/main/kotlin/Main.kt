package io.github.tolisso

import kotlinx.coroutines.*

const val PARALLELISM_BORDER = 10000

@OptIn(DelicateCoroutinesApi::class)
val context = newFixedThreadPoolContext(nThreads = 4, "QuickSortPool")

fun seq(arr: IntArray, left: Int, right: Int) {
    val (start, end) = partition(left, right, arr)

    if (left < end) {
        seq(arr, left, end)
    }
    if (start < right) {
        seq(arr, start, right)
    }
}

suspend fun par(arr: IntArray, left: Int, right: Int): Unit = coroutineScope {
    if (right - left < PARALLELISM_BORDER) {
        seq(arr, left, right)
        return@coroutineScope
    }

    val (start, end) = partition(left, right, arr)

    val job1 = async(context) {
        if (left < end) {
            par(arr, left, end)
        }
    }
    val job2 = async(context) {
        if (start < right) {
            par(arr, start, right)
        }
    }
    job1.join()
    job2.join()
}

private fun partition(left: Int, right: Int, arr: IntArray): Pair<Int, Int> {
    var start = left
    var end = right
    val pivot = arr[(left + right) / 2]

    while (start <= end) {
        while (arr[start] < pivot) {
            start++
        }
        while (arr[end] > pivot) {
            end--
        }
        if (start <= end) {
            val temp = arr[start]
            arr[start] = arr[end]
            arr[end] = temp
            start++
            end--
        }
    }
    return Pair(start, end)
}