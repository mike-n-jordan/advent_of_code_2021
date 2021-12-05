package com.mike.advent

fun main() {
    problem2_2()
}

private fun problem2_1() {
    inputSequence("problem2-1.txt")
        .map { it.toSubmarineAction() }
        .fold(SubmarineStateV1()) { acc, action -> acc.perform(action) }
        .let { it.depth * it.horizontalPosition }
        .let { println(it) }
}

private fun problem2_2() {
    inputSequence("problem2-1.txt")
        .map { it.toSubmarineAction() }
        .fold(SubmarineStateV2()) { acc, action -> acc.perform(action) }
        .let { it.depth * it.horizontalPosition }
        .let { println(it) }
}
