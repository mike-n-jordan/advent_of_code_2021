package com.mike.advent

fun main() {
    problem3_2()
}

private fun problem3_1() {
    inputSequence("problem3-1.txt")
        .map { it.map { it.digitToInt() } }
        .fold(List(12) { 0 } to 0) { acc, list ->
            acc.first.mapIndexed { index, i -> i + list[index] } to acc.second + 1
        }
        .let { pair -> pair.first.map { if (it < pair.second / 2f) 0 else 1 } }
        .let { list -> list to list.map { if (it == 1) 0 else 1 } }
        .let { pair -> pair.first.binaryToInt() * pair.second.binaryToInt() }
        .let { println(it) }
}

private fun List<Int>.binaryToInt() =
    joinToString("").toInt(2)

private fun problem3_2() {
    inputSequence("problem3-1.txt")
        .map { it.map { it.digitToInt() } }
        .toList()
        .let {
            it.findListBySelectedValue(0) { sum, listSize -> if (sum < listSize / 2f) 0 else 1 } to
                it.findListBySelectedValue(0) { sum, listSize -> if (sum >= listSize / 2f) 0 else 1}
        }
        .let { it.first.binaryToInt() * it.second.binaryToInt()  }
        .let { println(it) }
}

private tailrec fun List<List<Int>>.findListBySelectedValue(index: Int, selector: (sum: Int, listSize: Int) -> Int): List<Int> =
    when {
        isEmpty() -> throw IllegalArgumentException()
        size == 1 -> first()
        else -> {
            val selectedValue = fold(0) { acc, list -> acc + list[index] }
                .let { selector(it, size) }
            filter { it[index] != selectedValue }
                .findListBySelectedValue(index + 1, selector)
        }
    }
