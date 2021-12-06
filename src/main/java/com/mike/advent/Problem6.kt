package com.mike.advent

fun main() {
    problem6_2()
}

private fun problem6_1() {
    inputSequence("problem6-1.txt")
        .countFishForDays(80)
        .let { println(it) }
}

private fun problem6_2() {
    inputSequence("problem6-1.txt")
        .countFishForDays(256)
        .let { println(it) }
}

private fun Sequence<String>.countFishForDays(days: Int): Long =
        first()
        .split(",")
        .map { it.toInt() }
        .fold(mutableMapOf<Int, Long>()) { acc, i -> acc.also { it.put(i, it.getOrDefault(i, 0) + 1) } }
        .also { fishMap -> repeat(days) { fishMap.age() } }
        .values
        .sum()

private fun MutableMap<Int, Long>.age() {
    val newFish = this[0] ?: 0
    for (i in 0 until 8) {
        this[i] = this[i + 1] ?: 0
    }
    this[6] = (this[6] ?: 0) + newFish
    this[8] = newFish
}
