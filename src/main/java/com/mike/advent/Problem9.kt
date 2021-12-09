package com.mike.advent

fun main() {
    problem9_2()
}

private fun problem9_1() {
    inputSequence("problem9-1.txt")
        .map { it.map { it.digitToInt() } }
        .windowed(3, partialWindows = true)
        .mapIndexed { index, list -> list.sumRiskLevels(index == 0) }
        .sum()
        .let { println(it) }
}

private fun List<List<Int>>.sumRiskLevels(isFirst: Boolean): Int {
    var count = 0
    when {
        isFirst -> count += sumInternalRiskLevels(this[0], this[1]) + sumInternalRiskLevels(this[1], this[0], this[2])
        size == 3 -> count += sumInternalRiskLevels(this[1], this[0], this[2])
        size == 2 -> count += sumInternalRiskLevels(this[1], this[0])
    }
    return count
}

private fun sumInternalRiskLevels(read: List<Int>, neighbor: List<Int>, neighbor2: List<Int>? = null): Int {
    var count = 0
    for (i in read.indices) {
        if (isLowPoint(read[i], read[i - 1], read[i + 1], neighbor[i], neighbor2?.get(i))) {
            count += read[i] + 1
        }
    }
    return count
}

private fun problem9_2() {
    inputSequence("problem9-1.txt")
        .map { it.map { it.digitToInt() } }
        .map { it.toMutableList() }
        .toList()
        .findLargestBasins()
        .sortedDescending()
        .take(3)
        .reduce { acc, i -> acc * i }
        .let { println(it) }
}

private fun List<MutableList<Int>>.findLargestBasins(): List<Int> {
    fun safeGet(row: Int, column: Int): Int? =
        getOrNull(row)?.getOrNull(column)

    val results = ArrayList<Int>()

    for (row in 0 until size) {
        for (column in 0 until first().size) {
            if (
                isLowPoint(this[row][column], safeGet(row, column - 1), safeGet(row, column + 1),
                    safeGet(row - 1, column), safeGet(row + 1, column))
            ) {
                results.add(calculateSizeAndMarkBasin(row, column))
            }
        }
    }

    return results
}

private fun List<MutableList<Int>>.calculateSizeAndMarkBasin(row: Int, column: Int): Int {
    val value = getOrNull(row)?.getOrNull(column) ?: return 0
    this[row][column] = 9
    return when (value) {
        9 -> 0
        else -> 1 +
            calculateSizeAndMarkBasin(row - 1, column) +
            calculateSizeAndMarkBasin(row + 1, column) +
            calculateSizeAndMarkBasin(row, column + 1) +
            calculateSizeAndMarkBasin(row, column - 1)
    }
}

private fun isLowPoint(center: Int, left: Int?, right: Int?, top: Int?, bottom: Int?): Boolean =
    center < (left ?: Int.MAX_VALUE)
        && center < (right ?: Int.MAX_VALUE)
        && center < (top ?: Int.MAX_VALUE)
        && center < (bottom ?: Int.MAX_VALUE)
