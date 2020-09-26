package com.gailswintha.sudoku.solver.strategy

import com.gailswintha.sudoku.core.model.Board
import com.gailswintha.sudoku.core.model.Board.Companion.COLUMN_LENGTH
import com.gailswintha.sudoku.core.model.Board.Companion.ROW_LENGTH
import com.gailswintha.sudoku.core.model.Board.Companion.next
import com.gailswintha.sudoku.core.model.Position

class Deduction : Strategy {
    override val order = 0

    override fun complete(board: Board): Board = complete(Board(board), Board.FIRST_POSITION)

    private tailrec fun complete(board: Board, position: Position): Board = when {
        completeRowsWithOnlyOneMissingValue(board) ||
                completeColumnsWithOnlyOneMissingValue(board) ||
                completeSectionsWithOnlyOneMissingValue(board) ||
                completeByDeduction(board, position) -> complete(board, Board.FIRST_POSITION)
        position == Board.LAST_POSITION -> board
        else -> complete(board, position.next())
    }

    private fun completeByDeduction(board: Board, position: Position): Boolean = false // TODO: implement

    private fun completeRowsWithOnlyOneMissingValue(board: Board): Boolean = (0 until Board.ROW_LENGTH)
            .map { rowIndex ->
                val row = board.row(rowIndex).removeNoValues()
                if(row.size == Board.COLUMN_LENGTH - 1) {
                    Board.VALUE_RANGE.find { !row.contains(it) }
                            ?.let { missingValue -> board.setFirstNoValueInRow(rowIndex, missingValue) }
                            ?: false
                } else {
                    false
                }
            }
            .any { it }

    private fun completeColumnsWithOnlyOneMissingValue(board: Board): Boolean = (0 until Board.COLUMN_LENGTH)
            .map { columnIndex ->
                val column = board.column(columnIndex).removeNoValues()
                if(column.size == Board.COLUMN_LENGTH - 1) {
                    Board.VALUE_RANGE.find { !column.contains(it) }
                            ?.let { missingValue -> board.setFirstNoValueInColumn(columnIndex, missingValue) }
                            ?: false
                } else {
                    false
                }
            }
            .any { it }

    private fun completeSectionsWithOnlyOneMissingValue(board: Board): Boolean = Board.SECTION_CENTERS
            .map { sectionCenter ->
                val section = board.section(sectionCenter).removeNoValues()
                if(section.size == Board.SECTION_LENGTH - 1) {
                    Board.VALUE_RANGE.find { !section.contains(it) }
                            ?.let { missingValue -> board.setFirstNoValueInSection(sectionCenter, missingValue)}
                            ?: false
                } else {
                    false
                }
            }
            .any { it }

    private fun List<Int>.removeNoValues() = toMutableSet().also { it.remove(Board.NO_VALUE) }

    private fun Board.setFirstNoValueInRow(rowIndex: Int, value: Int): Boolean {
        tailrec fun recFn(columnIndex: Int): Boolean = when {
            columnIndex == COLUMN_LENGTH -> false
            !isSet(Position(rowIndex, columnIndex)) -> { this[Position(rowIndex, columnIndex)] = value; true }
            else -> recFn(columnIndex + 1)
        }
        return recFn(0)
    }

    private fun Board.setFirstNoValueInColumn(columnIndex: Int, value: Int): Boolean {
        tailrec fun recFn(rowIndex: Int): Boolean = when {
            rowIndex == ROW_LENGTH -> false
            !isSet(Position(rowIndex, columnIndex)) -> { this[Position(rowIndex, columnIndex)] = value; true }
            else -> recFn(rowIndex + 1)
        }
        return recFn(0)
    }

    private fun Board.setFirstNoValueInSection(sectionCenter: Position, value: Int): Boolean {
        val sectionPositions = Board.SECTION_POSITIONS[sectionCenter] ?: emptyList() // TODO: unit tests for SECTION_POSITIONS

        tailrec fun recFn(positionIndex: Int): Boolean = when {
            positionIndex == sectionPositions.size -> false
            !isSet(sectionPositions[positionIndex]) -> { this[sectionPositions[positionIndex]] = value; true }
            else -> recFn(positionIndex + 1)
        }
        return recFn(0)
    }
}