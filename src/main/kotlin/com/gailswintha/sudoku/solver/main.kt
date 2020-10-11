package com.gailswintha.sudoku.solver

import com.gailswintha.sudoku.core.io.loadFromArray
import com.gailswintha.sudoku.core.model.Board
import com.gailswintha.sudoku.core.model.Board.Companion.X
import com.gailswintha.sudoku.solver.strategy.SimpleDeduction

fun main() {

    val board = Board.loadFromArray(
            arrayOf(
                    arrayOf(9, 5, 6, 4, X, X, X, X, X),
                    arrayOf(X, 1, X, X, 9, 5, 6, X, X),
                    arrayOf(X, 7, X, X, X, X, X, X, X),
                    arrayOf(X, 2, 8, X, 4, X, 1, X, 9),
                    arrayOf(X, 3, X, 2, X, 6, X, 5, X),
                    arrayOf(7, X, 5, X, 3, X, 8, 2, X),
                    arrayOf(X, X, X, X, X, X, X, 7, X),
                    arrayOf(X, X, 4, 7, 5, X, X, 6, X),
                    arrayOf(X, X, X, X, X, 4, 5, 1, 2)
            )
    )

    println(board)
    println()
    val boardSimpleDeduction = SimpleDeduction().complete(board)
    println(boardSimpleDeduction)
}