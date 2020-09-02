package com.gailswintha.sudoku.sudokusolver

import com.gailswintha.sudoku.core.model.Board

fun main() {
    val table = Board()
    println(table)

    println()
    println(table.column(3).joinToString(separator = " "))
    println()
    println(table.row(2).joinToString(separator = " "))
}