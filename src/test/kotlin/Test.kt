import io.github.tolisso.par
import io.github.tolisso.seq
import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.system.measureTimeMillis

const val SMALL_ARRAY_SIZE = 1_000_000
const val ARRAY_SIZE = 100_000_000

class Test {

    private fun randArr(size: Int) =
        generateSequence { Random.nextInt() }.take(size).toList().toIntArray()

    @Test
    fun testSeqCorrectness() {
        fun test() {
            val arrOriginal = randArr(SMALL_ARRAY_SIZE)
            val arr = arrOriginal.copyOf()

            seq(arr, 0, SMALL_ARRAY_SIZE - 1)

            assert(arr.contentEquals(arrOriginal.sortedArray()))
        }

        repeat(10) {
            test()
        }
        println("test seq correctness passed")
    }

    @Test
    fun testParCorrectness() {
        fun test() {
            val arrOriginal = randArr(SMALL_ARRAY_SIZE)
            val arr = arrOriginal.copyOf()

            runBlocking {
                par(arr, 0, SMALL_ARRAY_SIZE - 1)
            }

            assert(arr.contentEquals(arrOriginal.sortedArray()))
        }

        repeat(10) {
            test()
        }
        println("test par correctness passed")
    }

    @Test
    fun compareSpeed() {
        fun testPar(): Long {
            val arr = randArr(ARRAY_SIZE)

            return measureTimeMillis {
                runBlocking {
                    par(arr, 0, ARRAY_SIZE - 1)
                }
            }
        }

        fun testSeq(): Long {
            val arr = randArr(ARRAY_SIZE)

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