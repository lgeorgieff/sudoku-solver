package com.gailswintha.sudoku.solver.strategy

import com.gailswintha.sudoku.core.model.Board

interface Strategy {
    fun complete(board: Board): Board
    val order: Int
}