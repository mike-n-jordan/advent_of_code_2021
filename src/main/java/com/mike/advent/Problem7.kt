package com.mike.advent

import kotlin.math.min

fun main() {
    problem7_2()
}

private fun problem7_1() {
    println(solveProblemWithCostFunction(::calculateFuelCostTo))
}

private fun problem7_2() {
    println(solveProblemWithCostFunction(::calculteNewFuelCostTo))
}

private fun solveProblemWithCostFunction(costFunction: (Map<Int, Int>, Int) -> Int): Int =
    inputSequence("problem7-1.txt")
        .first()
        .split(",")
        .map { it.toInt() }
        .fold(HashMap<Int, Int>()) { acc, i -> acc.also { it.put(i, (acc.getOrDefault(i, 0)) + 1) } }
        .let { map ->
            val maxKey = map.maxByOrNull { it.key }?.key ?: 0
            val function: (Int) -> Int = { costFunction(map, it) }
            binarySearch(0, maxKey, function)
        }

private fun calculateFuelCostTo(map: Map<Int, Int>, position: Int): Int =
    map.map { Math.abs((it.key - position) * it.value) }.sum()

private fun calculteNewFuelCostTo(map: Map<Int, Int>, position: Int): Int =
    map.map { (Math.abs(it.key - position).additionFactorial() * it.value) }.sum()

private fun Int.additionFactorial(): Int {
    var result = 0
    for (i in 1 .. this) {
        result += i
    }
    return result
}

private tailrec fun binarySearch(low: Int, high: Int, function: (Int) -> Int): Int {
    val middle = (low + high) / 2
    return when {
        low == high -> function(low)
        high - low == 1 -> min(function(low), function(high))
        function(middle + 1) > function(middle) -> binarySearch(low, middle, function)
        else -> binarySearch(middle, high, function)
    }
}
