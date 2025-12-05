fun part1(ranges: List<Pair<Long, Long>>, indexes: List<Long>): Int {
    val mergedRanges = ranges.sortAndMerge()
    return indexes.count { index ->
        val indexOrReversedInsertionPoint = mergedRanges.binarySearchBy(index) { it.first }
        val (begin, end) = when {
            indexOrReversedInsertionPoint >= 0 -> mergedRanges[indexOrReversedInsertionPoint]
            else -> mergedRanges[(-indexOrReversedInsertionPoint - 2).coerceAtLeast(0)]
        }
        index in begin..<end
    }
}

private fun List<Pair<Long, Long>>.sortAndMerge(): List<Pair<Long, Long>> {
    val sortedRanges = sortedBy { it.first }
    val mergedRanges = buildList {
        var (currentBegin, currentEnd) = sortedRanges.first()
        sortedRanges.asSequence().drop(1).forEach { (begin, end) ->
            if (begin <= currentEnd) {
                currentEnd = maxOf(currentEnd, end)
            } else {
                add(currentBegin to currentEnd)
                currentBegin = begin
                currentEnd = end
            }
        }
        add(currentBegin to currentEnd)
    }
    return mergedRanges
}

fun part2(ranges: List<Pair<Long, Long>>): Long {
    val mergedRanges = ranges.sortAndMerge()
    return mergedRanges.sumOf { (begin, end) -> end - begin }
}

fun main() {
    val rawInput = readInput("Day05")
    val emptyLineIndex = rawInput.indexOf("")
    check(emptyLineIndex != -1)
    val ranges = rawInput.asSequence()
        .take(emptyLineIndex)
        .map { it.split("-").map(String::toLong) }
        .map { (left, right) -> left to right + 1 }
        .toList()
    val ingredientIds = rawInput.asSequence()
        .drop(emptyLineIndex + 1)
        .map(String::toLong)
        .toList()
    println(part1(ranges, ingredientIds))
    println(part2(ranges))
}