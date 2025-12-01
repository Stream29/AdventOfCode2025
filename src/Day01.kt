fun part1(start: Int, rawInputs: List<String>): Int {
    val inputs = rawInputs.map {
        val direction = it[0]
        val sign = when(direction) {
            'L' -> -1
            'R' -> 1
            else -> throw IllegalArgumentException()
        }
        sign * it.substring(1).toInt()
    }
    return inputs.asSequence().runningFold(start) { acc, delta ->
        val sum = acc + delta
        (sum + 100) % 100
    }.count { it == 0 }
}

fun part2(start: Int, rawInputs: List<String>): Int {
    val inputs = rawInputs.asSequence().map {
        val direction = it[0]
        val sign = when (direction) {
            'L' -> -1
            'R' -> 1
            else -> throw IllegalArgumentException()
        }
        sign * it.substring(1).toInt()
    }

    val (_, count) = inputs.fold(start to 0) { (acc, count), delta ->
        val sum = acc + delta
        val deltaCount = if (sum < acc) (acc - 1).floorDiv(100) - (sum - 1).floorDiv(100)
        else sum.floorDiv(100) - acc.floorDiv(100)
        (sum + 100) % 100 to count + deltaCount
    }
    return count
}

fun main() {
    println(part1(50, readInput("Day01")))
    println(part2(50, readInput("Day01")))
}
