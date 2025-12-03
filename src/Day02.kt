import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.sign

fun BigInteger.repeated(times: Int): BigInteger = BigInteger(toString().repeat(times))

tailrec fun binarySearch(
    begin: BigInteger,
    end: BigInteger,
    predicate: (BigInteger) -> Boolean
): Pair<BigInteger, BigInteger> {
    val length = end - begin
    if (length == 1.toBigInteger()) return begin to end
    val mid = begin + length / 2.toBigInteger()
    val nextBegin: BigInteger
    val nextEnd: BigInteger
    if (predicate(mid)) {
        nextBegin = mid
        nextEnd = end
    } else {
        nextBegin = begin
        nextEnd = mid
    }
    return binarySearch(nextBegin, nextEnd, predicate)
}

fun part1(inputs: List<Pair<BigInteger, BigInteger>>, repeatTimes: Int): BigInteger {
    fun indexOf(n: BigInteger): BigInteger =
        binarySearch(0.toBigInteger(), n) { it.repeated(repeatTimes) <= n }.second
    return inputs.asSequence().map { (l, r) ->
        indexOf(l - 1.toBigInteger()) to indexOf(r)
    }.flatMap { (begin, end) ->
        sequence {
            var current = begin
            while (current < end) {
                yield(current)
                current++
            }
        }
    }.map { it.repeated(repeatTimes) }.fold(0.toBigInteger(), BigInteger::add)
}

fun main() {
    val inputs = readInput("Day02").first()
        .split(',')
        .map {
            val (left, right) = it.split('-')
            BigInteger(left) to BigInteger(right)
        }
    println(part1(inputs, 2))
    println(
        listOf(2, 3, 5, -6, 7, 8, -10, 11)
            .map { it.sign.toBigInteger() * part1(inputs, abs(it)) }
            .fold(0.toBigInteger(), BigInteger::add)
    )
}
