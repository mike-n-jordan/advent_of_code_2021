package com.mike.advent

typealias MutableBoard = MutableList<List<Int>>

fun main() {
    problem4_2()
}

private fun problem4_1() {
    inputSequence("problem4-1.txt")
        .fold(BingoGameBuilder()) { acc, s -> acc.attachLine(s) }
        .calculateFastestScore()
        .let { it.first * it.second }
        .let { println(it) }
}

private fun problem4_2() {
    inputSequence("problem4-1.txt")
        .fold(BingoGameBuilder()) { acc, s -> acc.attachLine(s) }
        .calculateSlowestScore()
        .let { it.first * it.second }
        .let { println(it) }
}

data class BingoGameBuilder(
    val numbers: MutableList<Int> = mutableListOf(),
    val boards: MutableList<MutableBoard> = mutableListOf(),
)

private fun BingoGameBuilder.attachLine(line: String): BingoGameBuilder {
    when {
        numbers.isEmpty() -> numbers.addAll(line.toIntList(","))
        line.isBlank() -> boards.add(mutableListOf())
        else -> boards.last().add(line.toIntList())
    }
    return this
}

private fun String.toIntList(spacer: String = " "): List<Int> =
    split(spacer).filter { it.isNotBlank() }.map { it.toInt() }

private fun BingoGameBuilder.calculateSlowestScore(): Pair<Int, Int> {
    val set = mutableSetOf<Int>()
    var index = 0

    while (true) {
        set.add(numbers[index])

        val iterator = boards.iterator()
        while(iterator.hasNext()) {
            val board = iterator.next()
            if (board.isWon(set)) {
                if (boards.size == 1) {
                    return numbers[index] to board.calculateScore(set)
                } else {
                    iterator.remove()
                }
            }
        }

        index++
    }
}

private fun BingoGameBuilder.calculateFastestScore(): Pair<Int, Int> {
    val set = mutableSetOf<Int>()
    var index = 0

    while (true) {
        set.add(numbers[index])

        for (board in boards) {
            if (board.isWon(set)) {
                return numbers[index] to board.calculateScore(set)
            }
        }

        index++
    }
}

private fun MutableBoard.isWon(set: Set<Int>): Boolean {
    row@ for (row in this) {
        for (value in row) {
            if (!set.contains(value)) {
                continue@row
            }
        }
        return true
    }

    val columnLength = first().size
    column@ for (index in 0 until columnLength) {
        for (row in this) {
            if (!set.contains(row[index])) {
                continue@column
            }
        }
        return true
    }

    return false
}

private fun MutableBoard.calculateScore(set: Set<Int>): Int {
    var sum = 0
    for (row in this) {
        for (value in row) {
            if (!set.contains(value)) {
                sum += value
            }
        }
    }
    return sum
}
