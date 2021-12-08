package com.mike.advent

import java.lang.IllegalStateException

fun main() {
    problem8_2()
}

private fun problem8_1() {
    inputSequence("problem8-1.txt")
        .map { it.extractOutputValues() }
        .flatten()
        .map {
            when (it.size) {
                7, 4, 2, 3 -> 1
                else -> 0
            }
        }
        .sum()
        .let { println(it) }
}

private fun problem8_2() {
    inputSequence("problem8-1.txt")
        .map { it.extractInputValues().toDecoder() to it.extractOutputValues() }
        .map { (decoder, output) -> output.map { decoder[it]!! } }
        .map { it.joinToString("").toInt() }
        .sum()
        .let { println(it) }
}

private fun String.extractOutputValues(): List<Set<Char>> =
    substring(indexOf("| ") + 2).split(" ").map { it.toSet() }

private fun String.extractInputValues(): List<Set<Char>> =
    substring(0, indexOf(" |")).split(" ").map { it.toSet() }

private fun List<Set<Char>>.toDecoder(): Map<Set<Char>, Int> {
    val decoder = mutableMapOf<Set<Char>, Int>()
    val recoder = mutableMapOf<Int, Set<Char>>()

    fun Set<Char>.assignTo(int: Int) {
        decoder[this] = int
        recoder[int] = this
    }

    for (set in this) {
        when(set.size) {
            2 -> set.assignTo(1)
            3 -> set.assignTo(7)
            4 -> set.assignTo(4)
            7 -> set.assignTo(8)
        }
    }

    for (set in this) {
        when {
            decoder[set] != null -> continue
            set.size == 6 && set.containsAll(recoder[4]!!) && set.containsAll(recoder[7]!!) -> set.assignTo(9)
            set.size == 6 && set.containsAll(recoder[1]!!) && set.diff(recoder[4]!!) == 1 -> set.assignTo(0)
            set.size == 6 -> set.assignTo(6)
            set.size == 5 && set.containsAll(recoder[1]!!) -> set.assignTo(3)
            set.size == 5 && set.diff(recoder[4]!!) == 1 -> set.assignTo(5)
            set.size == 5 -> set.assignTo(2)
            else -> throw IllegalStateException()
        }
    }

    return decoder
}

private fun Set<Char>.diff(other: Set<Char>): Int {
    var missCount = 0

    for (char in other) {
        missCount += if (contains(char)) 0 else 1
    }

    return missCount
}
