import io.github.tolisso.par
import io.github.tolisso.seq
import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

class Test {

    @Test
    fun testCorrectness() {
        testCorrectnessAbstract { seq(it) }
        testCorrectnessAbstract { par(it) }
    }

    private fun testCorrectnessAbstract(f: (List<List<Int>>) -> List<Int>) {
        assertEquals(listOf(0, 1, 2, 3, 4), f(
            listOf(
                listOf(1),
                listOf(2),
                listOf(3),
                listOf(4),
                listOf(),
            )
        ))

        assertEquals(listOf(0, 1, 2, 1, 2), f(
            listOf(
                listOf(1, 3),
                listOf(2),
                listOf(3),
                listOf(4),
                listOf(),
            )
        ))

        assertEquals(listOf(0, 1, 2, 2, 1), f(
            listOf(
                listOf(1, 4),
                listOf(2, 0),
                listOf(3, 1),
                listOf(4, 2),
                listOf(0, 3),
            )
        ))

        println("test seq correctness passed")
    }

    private fun generateCubicGraph(): List<List<Int>> {
        val size = 450
        fun cordToIndex(x: Int, y: Int, z: Int) =
            if (listOf(x, y, z).any { it >= size }) {
                null
            } else {
                ((x * size) + y) * size + z
            }

        val res = mutableListOf<List<Int>>()

        (0 until size).forEach { x ->
            (0 until size).forEach { y ->
                (0 until size).forEach { z ->
                    res.add(listOfNotNull(
                        cordToIndex(x + 1, y, z),
                        cordToIndex(x, y + 1, z),
                        cordToIndex(x, y, z + 1),
                    ))
                }
            }
        }
        return res
    }

    @Test
    fun compareSpeed() {
        val graph = generateCubicGraph()
        fun testPar() = measureTimeMillis {
            par(graph)
        }

        fun testSeq() = measureTimeMillis {
            seq(graph)
        }

        val parTime = (1..5).map { testPar() }.average()
        val seqTime = (1..5).map { testSeq() }.average()

        println("seq time = $seqTime, par time = $parTime, par faster in ${seqTime / parTime}")
        assert(seqTime / parTime > 3)
    }

}