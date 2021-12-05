package com.mike.advent

class Util {

}

fun inputSequence(file: String): Sequence<String> =
    Util::class.java.classLoader.getResourceAsStream("input/$file")!!
        .bufferedReader()
        .lineSequence()
