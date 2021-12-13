package com.mike.advent

fun main() {
    problem13_2()
}

data class Origami(
    val dots: Set<Pair<Int, Int>> = emptySet(),
    val instructions: List<Instruction> = emptyList(),
)

sealed interface Instruction {
    data class FoldUp(val atY: Int) : Instruction
    data class FoldLeft(val atX: Int) : Instruction
}

private fun problem13_1() {
    inputSequence("problem13-1.txt")
        .toOrigami()
        .let { it.copy(instructions = listOf(it.instructions.first())) }
        .applyInstructions()
        .dots
        .size
        .let { println(it) }
}

private fun problem13_2() {
    inputSequence("problem13-1.txt")
        .toOrigami()
        .applyInstructions()
        .fancyPrint()
}

private fun Sequence<String>.toOrigami() =
    fold(Origami()) { acc, s ->
        when {
            s.contains(",") -> s.split(",").map { it.toInt() }
                .let { acc.copy(dots = acc.dots.plus(it[0] to it[1])) }
            s.isBlank() -> acc
            s.contains("fold") && s.contains("y") -> s.substring(s.indexOf("=") + 1)
                .toInt()
                .let { Instruction.FoldUp(it) }
                .let { acc.copy(instructions = acc.instructions.plus(it)) }
            s.contains("fold") && s.contains("x") -> s.substring(s.indexOf("=") + 1)
                .toInt()
                .let { Instruction.FoldLeft(it) }
                .let { acc.copy(instructions = acc.instructions.plus(it)) }
            else -> throw IllegalArgumentException()
        }
    }

private tailrec fun Origami.applyInstructions(): Origami =
    when (val instruction = instructions.firstOrNull()) {
        null -> this
        else -> copy(dots = dots.applyInstruction(instruction), instructions = instructions.minus(instruction))
            .applyInstructions()
    }

private fun Set<Pair<Int, Int>>.applyInstruction(instruction: Instruction): Set<Pair<Int, Int>> =
    map {
        when (instruction) {
            is Instruction.FoldLeft ->
                if (it.first < instruction.atX) {
                    it
                } else {
                    (instruction.atX - (it.first - instruction.atX)) to it.second
                }
            is Instruction.FoldUp ->
                if (it.second < instruction.atY) {
                    it
                } else {
                    it.first to (instruction.atY - (it.second - instruction.atY))
                }
        }
    }.toSet()

private fun Origami.fancyPrint() {
    val maxX = dots.maxByOrNull { it.first }!!.first + 1
    val maxY = dots.maxByOrNull { it.second }!!.second + 1
    val grid = Array(maxY) { CharArray(maxX) { ' ' } }

    dots.forEach { grid[it.second][it.first] = '*' }

    for (row in grid) {
        row.concatToString().let { println(it) }
    }
}
