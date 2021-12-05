package com.mike.advent

import java.lang.Integer.max
import java.lang.Integer.min


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
    if (line.first.x != line.second.x && line.first.y == line.second.y) {
        var min = min(line.first.x, line.second.x)
        val max = max(line.first.x, line.second.x)
        while (min <= max) {
            val row = grid.getOrPut(min) { HashMap() }
            val newValue = row.getOrDefault(line.second.y, 0) + 1
            row.put(line.second.y, newValue)
            if (newValue == 2) count++
            min++
        }
    } else if (line.first.x == line.second.x && line.first.y != line.second.y) {
        var min = min(line.first.y, line.second.y)
        val max = max(line.first.y, line.second.y)
        val row = grid.getOrPut(line.first.x) { HashMap() }
        while (min <= max) {
            val newValue = row.getOrDefault(min, 0) + 1
            row.put(min, newValue)
            if (newValue == 2) count++
            min++
        }
    }
    else if (diagonalEnabled) {
        val minXLine = if (line.first.x < line.second.x) line.first else line.second
        val maxXLine = if (minXLine === line.first) line.second else line.first
        val yIncrement = if (minXLine.y < maxXLine.y) 1 else -1

        var min = minXLine.x
        var minY = minXLine.y
        val max = maxXLine.x
        while (min <= max) {
            val row = grid.getOrPut(min) { HashMap() }
            val newValue = row.getOrDefault(minY, 0) + 1
            row.put(minY, newValue)
            if (newValue == 2) count++
            min++
            minY += yIncrement
        }
    }

    return this
}
