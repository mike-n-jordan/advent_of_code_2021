package com.mike.advent

fun main() {
    problem11_2()
}

private fun problem11_1() {
    inputSequence("problem11-1.txt")
        .map { it.map { it.digitToInt() }.toMutableList() }
        .toList()
        .simulateFlashesFor(100)
        .let { println(it) }
}

private fun List<MutableList<Int>>.simulateFlashesFor(days: Int): Int {
    var flashes = 0

    repeat(days) {
        flashes += simulateSingleStep()
    }

    return flashes
}

private fun problem11_2() {
    inputSequence("problem11-1.txt")
        .map { it.map { it.digitToInt() }.toMutableList() }
        .toList()
        .simulateFlashesUntilAllFlash()
        .let { println(it) }
}

private fun List<MutableList<Int>>.simulateFlashesUntilAllFlash(): Int {
    val targetFlashes = size * this[0].size

    var step = 0
    while (true) {
        step++
        if (simulateSingleStep() == targetFlashes) {
            break
        }
    }

    return step
}

private fun List<MutableList<Int>>.simulateSingleStep(): Int {
    var flashes = 0

    for (row in 0 until size) {
        for (column in 0 until this[0].size) {
            incrementFlashCounter(row, column)
        }
    }

    for (row in 0 until size) {
        for (column in 0 until this[0].size) {
            if (this[row][column] > 9) {
                flashes++
                this[row][column] = 0
            }
        }
    }

    return flashes
}

private fun List<MutableList<Int>>.incrementFlashCounter(x: Int, y: Int) {
    val check = getOrNull(x)?.getOrNull(y) ?: return
    val newValue = this[x][y] + 1
    this[x][y] = newValue
    if (newValue == 10) {
        for (newX in -1..1) {
            for (newY in -1..1) {
                incrementFlashCounter(x + newX, y + newY)
            }
        }
    }
}
