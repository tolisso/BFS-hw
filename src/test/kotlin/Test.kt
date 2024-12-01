import io.github.tolisso.par
import io.github.tolisso.seq
import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

const val SMALL_ARRAY_SIZE = 1_000_000
const val ARRAY_SIZE = 100_000_000

class Test {
    @Test
    fun testSeqCorrectness() {
        fun test() {
            val arrSorted = (0 until SMALL_ARRAY_SIZE).toList().toIntArray()
            val arr = arrSorted.copyOf()

            arr.shuffle()
            seq(arr, 0, SMALL_ARRAY_SIZE - 1)

            assert(arr.contentEquals(arrSorted))
        }

        repeat(10) {
            test()
        }
        println("test seq correctness passed")
    }

    @Test
    fun testParCorrectness() {
        fun test() {
            val arrSorted = (0 until SMALL_ARRAY_SIZE).toList().toIntArray()
            val arr = arrSorted.copyOf()

            arr.shuffle()
            runBlocking {
                par(arr, 0, SMALL_ARRAY_SIZE - 1)
            }

            assert(arr.contentEquals(arrSorted))
        }

        repeat(10) {
            test()
        }
        println("test par correctness passed")
    }

    @Test
    fun compareSpeed() {
        fun testPar(): Long {
            val arr = (0 until ARRAY_SIZE).toList().toIntArray()
            arr.shuffle()

            return measureTimeMillis {
                runBlocking {
                    par(arr, 0, ARRAY_SIZE - 1)
                }
            }
        }

        fun testSeq(): Long {
            val arr = (0 until ARRAY_SIZE).toList().toIntArray()
            arr.shuffle()

            return measureTimeMillis {
                seq(arr, 0, ARRAY_SIZE - 1)
            }
        }

        val parTime = (1..5).map { testPar() }.average()
        val seqTime = (1..5).map { testSeq() }.average()

        assert(seqTime / parTime > 3)
        println("seq time = $seqTime, par time = $parTime, par faster in ${seqTime / parTime}")
    }

}