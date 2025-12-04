val directions = listOf(-1, 0, 1)
    .flatMap { x -> listOf(-1, 0, 1).map { y -> x to y } }
    .filter { (x, y) -> x != 0 || y != 0 }

fun part1(input: List<List<Char>>): Int {
    val xSize = input.size
    val ySize = input.first().size
    fun access(x: Int, y: Int): Char {
        if (x !in 0..<xSize) return '.'
        if (y !in 0..<ySize) return '.'
        return input[x][y]
    }
    return (0..<xSize).sumOf { x ->
        (0..<ySize)
            .filter { y -> access(x, y) == '@' }
            .count { y -> directions.count { (deltaX, deltaY) -> access(x + deltaX, y + deltaY) == '@' } < 4 }
    }
}

fun part2(input: List<List<Char>>): Int {
    val xSize = input.size
    val ySize = input.first().size

    val map = input.map { it.toMutableList() }
    fun access(x: Int, y: Int): Char {
        if (x !in 0..<xSize) return '.'
        if (y !in 0..<ySize) return '.'
        return map[x][y]
    }

    fun tryRemove(): Int =
        (0..<xSize).sumOf { x ->
            (0..<ySize)
                .filter { y -> access(x, y) == '@' }
                .filter { y -> directions.count { (deltaX, deltaY) -> access(x + deltaX, y + deltaY) == '@' } < 4 }
                .onEach { y -> map[x][y] = '.' }
                .count()
        }

    return generateSequence { tryRemove().takeIf { it != 0 } }.sum()
}

fun main() {
    val input = readInput("Day04").map { it.toList() }
    println(part1(input))
    println(part2(input))
}