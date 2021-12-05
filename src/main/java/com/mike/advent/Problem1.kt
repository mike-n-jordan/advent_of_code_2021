package com.mike.advent

fun main() {
    problem1_2()
}

private fun problem1_1() {
    inputSequence("problem1-1.txt")
        .map { it.toInt() }
        .fold(-1 to 0) { acc, i -> i to (acc.second + if (acc.first < i) 1 else 0) }
        .second
        .let { println(it - 1) }
}

private fun problem1_2() {
    inputSequence("problem1-1.txt")
        .map { it.toInt() }
        .windowed(3)
        .map { it.sum() }
        .fold(-1 to 0) { acc, i -> i to (acc.second + if (acc.first < i) 1 else 0) }
        .second
        .let { println(it - 1) }
}
