package com.mike.advent

fun main() {
    problem5_2()
}

private fun problem5_1() {
    inputSequence("problem5-1.txt")
        .map { line -> line.toPointPair() }
        .fold(Board()) { acc, i -> acc.drawVerticalAndHorizontal(i) }
        .let { println(it.count) }
}

private fun problem5_2() {
    inputSequence("problem5-1.txt")
        .map { line -> line.toPointPair() }
        .fold(Board()) { acc, i -> acc.drawVerticalAndHorizontal(i, diagonalEnabled = true) }
        .let { println(it.count) }
}

private fun String.toPointPair(): Pair<Point, Point> =
    split(" -> ")
        .map {
            it.split(",")
                .let { Point(it.first().toInt(), it.last().toInt()) }
        }
        .let { it.first() to it.last() }

data class Point(
    val x: Int,
    val y: Int,
)

class Board(
    var count: Int = 0,
    val grid: HashMap<Int, HashMap<Int, Int>> = HashMap()
)

private fun Board.drawVerticalAndHorizontal(line: Pair<Point, Point>, diagonalEnabled: Boolean = false): Board {
    if (
        diagonalEnabled
        || line.first.x == line.second.x && line.first.y != line.second.y
        || line.first.x != line.second.x && line.first.y == line.second.y
    ) {
        val minXLine = if (line.first.x < line.second.x) line.first else line.second
        val maxXLine = if (minXLine === line.first) line.second else line.first
        val yIncrement = if (minXLine.y == maxXLine.y) 0 else if (minXLine.y < maxXLine.y) 1 else -1
        val xIncrement = if (minXLine.x == maxXLine.x) 0 else 1

        var minX = minXLine.x
        var minY = minXLine.y
        while (minX - xIncrement != maxXLine.x || minY - yIncrement != maxXLine.y) {
            val row = grid.getOrPut(minX) { HashMap() }
            val newValue = row.getOrDefault(minY, 0) + 1
            row[minY] = newValue
            if (newValue == 2) count++
            minX += xIncrement
            minY += yIncrement
        }
    }

    return this
}
