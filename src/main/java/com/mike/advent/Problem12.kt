package com.mike.advent

fun main() {
    problem12_1()
}

sealed interface Cave {

    object Start : Cave

    data class BigCave(val name: String) : Cave

    data class SmallCave(val name: String) : Cave

    object End : Cave
}

private fun problem12_1() {
    inputSequence("problem12-1.txt")
        .toCaveMap()
        .let { dfs(Cave.Start, it, it.keys.size) }
        .let { println(it) }
}

private fun Sequence<String>.toCaveMap(): Map<Cave, List<Cave>> =
    map { it.split("-").map { it.toCave() } }
        .map { it[0] to it[1] }
        .toList()
        .let { it.plus(it.map { it.second to it.first }) }
        .groupBy { it.first }
        .mapValues { it.value.map { it.second } }

private fun String.toCave(): Cave =
    when {
        this == "start" -> Cave.Start
        this == "end" -> Cave.End
        lowercase() == this -> Cave.SmallCave(this)
        else -> Cave.BigCave(this)
    }

private fun dfs(
    current: Cave,
    map: Map<Cave, List<Cave>>,
    limit: Int,
    visited: Set<Cave> = emptySet(),
    length: Int = 0
): Int {
    if (length > limit) return 0
    if (current is Cave.End) return 1
    val nextOptions = map[current]?.filter { !visited.contains(it) } ?: emptyList()

    val newVisited = when (current) {
        is Cave.End,
        is Cave.BigCave -> visited
        is Cave.SmallCave,
        is Cave.Start -> visited.plus(current)
    }

    return nextOptions.map { dfs(it, map, limit, newVisited, length + 1) }.sum()
}
