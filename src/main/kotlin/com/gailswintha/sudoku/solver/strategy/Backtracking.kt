package com.gailswintha.sudoku.solver.strategy

import com.gailswintha.sudoku.core.model.Board
import com.gailswintha.sudoku.core.model.Board.Companion.FIRST
import com.gailswintha.sudoku.core.model.Board.Companion.isLast
import com.gailswintha.sudoku.core.model.Board.Companion.next
import com.gailswintha.sudoku.core.model.InvalidBoard
import com.gailswintha.sudoku.core.model.Position

class Backtracking : Strategy {
    override val order = 100

    private fun Board.getValuesToBeTried(position: Position) =
            Board.VALUE_RANGE.toList() - (section(position) + column(position.column) + row(position.row))

    override fun complete(board: Board): Board = complete(Board(board), board, Position.FIRST)

    private fun complete(board: Board, originalState: Board, position: Position): Board {
        return when {
            board.isValid -> {
                board
            }
            !originalState.isSet(position) -> {
                for (valueToBeTried in board.getValuesToBeTried(position)) {
                    if (board.isValid) return board
                    board[position] = valueToBeTried
                    if (!position.isLast) complete(board, originalState, position.next())
                }
                if(!board.isValid) board.unset(position)
                board
            }
            originalState.isSet(position) && !position.isLast -> {
                complete(board, originalState, position.next())
            }
            else -> {
                throw InvalidBoard(originalState)
            }
        }
    }
}