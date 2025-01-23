import io.github.tolisso.par
import io.github.tolisso.seq
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

class Test {

    @Test
    fun testCorrectness() {
        testCorrectnessAbstract { seq(it) }
        testCorrectnessAbstract { par(it) }

        val random = Random(1)
        repeat(5) {
            val graph = generateRandomGraph(random, graphSize = 50000)
            assertEquals(seq(graph), par(graph))
        }
    }

    private fun testCorrectnessAbstract(f: (List<List<Int>>) -> List<Int>) {
        assertEquals(
            listOf(0, 1, 2, 3, 4), f(
                listOf(
                    listOf(1),
                    listOf(2),
                    listOf(3),
                    listOf(4),
                    listOf(),
                )
            )
        )

        assertEquals(
            listOf(0, 1, 2, 1, 2), f(
                listOf(
                    listOf(1, 3),
                    listOf(2),
                    listOf(3),
                    listOf(4),
                    listOf(),
                )
            )
        )

        assertEquals(
            listOf(0, 1, 2, 2, 1), f(
                listOf(
                    listOf(1, 4),
                    listOf(2, 0),
                    listOf(3, 1),
                    listOf(4, 2),
                    listOf(0, 3),
                )
            )
        )

        // tested with http://e-maxx.ru/algo/bfs implementation
        assertEquals(
            listOf(
                0, 4, -1, 6, 5, 3, 4, 4, 2, 4, 3, 4, 4, 4, -1, 5, 4, 4, 4, 4, 3, 4, 2, 4, -1, 2, 2, 3, 5,
                3, 2, 2, 4, 4, 5, 4, 5, 2, 4, 4, 4, 4, 3, 1, 5, 5, 3, 3, 6, 5, 4, -1, 3, 5, 5, 3, 2, 3, 5, 3, 4, 5, 5,
                3, 4, 2, 1, 3, 3, 3, 1, 2, 2, 3, 5, 4, 3, 3, 3, 5, 1, 4, 3, 1, 4, 5, 6, 2, 5, 2, 2, 4, 4, 4, 4, 3, 4,
                4, 4, 3, 4
            ), f(
                listOf(
                    listOf(43, 66, 70, 80, 83),
                    listOf(31, 49, 68, 89),
                    listOf(31, 32, 86),
                    listOf(12, 26, 58, 80),
                    listOf(1, 48, 50, 72, 86, 92),
                    listOf(9, 23),
                    listOf(9, 53, 74),
                    listOf(34, 88),
                    listOf(5, 46, 52),
                    listOf(100),
                    listOf(1, 7, 11, 16, 22, 26, 73, 75, 99),
                    listOf(32, 61, 63, 67, 81),
                    listOf(4, 22, 34, 36),
                    listOf(1, 42, 71),
                    listOf(1, 41, 82, 96),
                    listOf(3, 19, 25, 27, 66, 69),
                    listOf(17, 61, 66),
                    listOf(26, 35, 45),
                    listOf(29),
                    listOf(34, 85),
                    listOf(21, 39, 77, 83, 92),
                    listOf(85, 100),
                    listOf(29),
                    listOf(44, 100),
                    listOf(52, 54),
                    listOf(30, 55, 56),
                    listOf(47, 57, 66, 67, 78),
                    listOf(18, 19, 60, 77),
                    listOf(17, 72),
                    listOf(35),
                    listOf(77),
                    listOf(63),
                    listOf(7, 97),
                    listOf(34, 35),
                    listOf(21, 84, 98),
                    listOf(52, 63),
                    listOf(6, 28, 44, 54, 87, 93),
                    listOf(10, 82),
                    listOf(0, 44, 67, 85),
                    listOf(4, 6, 21, 23, 28, 66),
                    listOf(23, 33, 44, 66, 78, 90),
                    listOf(27, 66, 69),
                    listOf(35, 38, 40, 82),
                    listOf(65, 83, 87),
                    listOf(19, 40, 48, 97),
                    listOf(7, 69),
                    listOf(92),
                    listOf(0, 8, 75, 84, 91),
                    listOf(31, 76, 79, 83),
                    listOf(),
                    listOf(7, 10, 35, 57, 92),
                    listOf(2, 31, 40, 55, 58),
                    listOf(27, 32, 76, 80),
                    listOf(99),
                    listOf(15, 39, 49, 79, 96),
                    listOf(0, 16, 30, 47, 98),
                    listOf(),
                    listOf(9, 12, 16),
                    listOf(9, 31, 38, 60),
                    listOf(13, 41, 47),
                    listOf(17, 25, 35, 99),
                    listOf(46, 89, 97),
                    listOf(42),
                    listOf(6, 37, 87, 96),
                    listOf(),
                    listOf(27, 59, 71),
                    listOf(89, 90),
                    listOf(16, 17, 55, 59, 63, 77, 90),
                    listOf(25, 33, 81),
                    listOf(5, 9, 13, 82, 100),
                    listOf(8, 30, 37, 65, 72, 80),
                    listOf(69),
                    listOf(42, 76, 99),
                    listOf(77, 93, 96, 97),
                    listOf(36, 48, 58, 62),
                    listOf(18, 31, 32, 38, 58, 73, 85),
                    listOf(9, 32, 64, 94),
                    listOf(8),
                    listOf(80, 91),
                    listOf(3, 59, 88, 95),
                    listOf(25, 26, 43, 56, 71),
                    listOf(16),
                    listOf(16, 50),
                    listOf(22, 31),
                    listOf(9, 41, 49, 50, 72, 100),
                    listOf(39, 84),
                    listOf(85),
                    listOf(73, 95),
                    listOf(3),
                    listOf(68),
                    listOf(20),
                    listOf(39, 54, 62, 94),
                    listOf(31, 69, 75, 79),
                    listOf(69),
                    listOf(32, 49, 58, 87, 97),
                    listOf(0, 56, 57),
                    listOf(20, 97),
                    listOf(26, 28, 29, 44, 85),
                    listOf(41, 45, 54, 55),
                    listOf(22, 66, 71),
                    listOf(15, 39, 81, 94)
                )
            )
        )
    }

    private fun generateRandomGraph(random: Random, graphSize: Int): List<List<Int>> = (0..graphSize).map { i ->
        (0..graphSize).mapNotNull { j ->
            if (i != j && random.nextDouble(0.0, 1.0) < 3.0 / graphSize) {
                j
            } else {
                null
            }
        }
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
                    res.add(
                        listOfNotNull(
                            cordToIndex(x + 1, y, z),
                            cordToIndex(x, y + 1, z),
                            cordToIndex(x, y, z + 1),
                        )
                    )
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