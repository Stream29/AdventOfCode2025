fun part1(input: List<List<Int>>): Int {
    fun <T : Comparable<T>> List<T>.maxIndex(begin: Int, end: Int) =
        (begin until end).asSequence().maxBy(this::get)
    return input.sumOf {
        val firstIndex = it.maxIndex(0, it.size - 1)
        val secondIndex = it.maxIndex(firstIndex + 1, it.size)
        it[firstIndex] * 10 + it[secondIndex]
    }
}

fun Long.pow(n: Int): Long = (1..n).fold(1L) { acc, _ -> acc * this }

fun part2(input: List<List<Int>>): Long {
    return input.sumOf { digits ->
        val memory = List(digits.size) { MutableList(digits.size) { 0L } }
        fun maxIn(begin: Int, length: Int): Long {
            if (length == 0) return 0L
            if (begin == digits.size) return 0L
            if (begin + length > digits.size) TODO("unreachable")
            if (memory[begin][length] != 0L) return memory[begin][length]
            memory[begin][length] = digits[begin] * 10L.pow(length - 1) + maxIn(begin + 1, length - 1)
            if (begin + length < digits.size) {
                memory[begin][length] = maxOf(
                    memory[begin][length],
                    maxIn(begin + 1, length)
                )
            }
            return memory[begin][length]
        }
        maxIn(0, 12)
    }
}

fun main() {
    val input = readInput("Day03").map { it.map { c -> c.toString().toInt() } }
    println(part1(input))
    println(part2(input))
}