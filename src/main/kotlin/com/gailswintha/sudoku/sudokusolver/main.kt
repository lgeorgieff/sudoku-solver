package com.gailswintha.sudoku.sudokusolver

import com.gailswintha.sudoku.core.model.Board

fun main() {
    val table = Board() // Board.loadFromJsonFile(File("/home/lukas/Development/github/sudoku-solver/board.json"))
    println(table)

    println()
    println(table.column(3).joinToString(separator = " "))
    println()
    println(table.row(2).joinToString(separator = " "))
}