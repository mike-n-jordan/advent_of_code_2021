package com.mike.advent

fun main() {
    problem10_2()
}

private fun problem10_1() {
    inputSequence("problem10-1.txt")
        .map { it.toCharArray() }
        .map { it.parseState() }
        .map { it.illegalCharacter }
        .filterNotNull()
        .map {
            when (it) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> throw IllegalStateException()
            }
        }
        .sum()
        .let { println(it) }
}

private fun problem10_2() {
    inputSequence("problem10-1.txt")
        .map { it.toCharArray() }
        .map { it.parseState() }
        .filter { it.illegalCharacter == null }
        .map { it.list }
        .map {
            it.fold(0L) { acc, c ->
                val multipliedBy5 = acc * 5
                val singleScore = when (c) {
                    ')' -> 1
                    ']' -> 2
                    '}' -> 3
                    '>' -> 4
                    else -> throw IllegalStateException()
                }
                multipliedBy5 + singleScore
            }
        }
        .sorted()
        .toList()
        .let { it[it.size / 2] }
        .let { println(it) }
}

private val openingBrackets = setOf<Char>(
    '[',
    '{',
    '<',
    '(',
)

private fun Char.toClosing(): Char =
    when (this) {
        '[' -> ']'
        '(' -> ')'
        '<' -> '>'
        '{' -> '}'
        else -> throw IllegalArgumentException()
    }

private fun CharArray.parseState(): ParseState =
    fold(ParseState()) { acc, c ->
        if (acc.illegalCharacter == null) {
            when {
                openingBrackets.contains(c) -> {
                    acc.also { it.list.addFirst(c.toClosing()) }
                }
                acc.list.removeFirst() == c -> {
                    acc
                }
                else -> {
                    acc.also { it.illegalCharacter = c }
                }
            }
        } else {
            acc
        }
    }

data class ParseState(
    val list: ArrayDeque<Char> = ArrayDeque(),
    var illegalCharacter: Char? = null
)
