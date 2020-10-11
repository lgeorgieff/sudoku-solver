package com.gailswintha.sudoku.solver.strategy

import com.gailswintha.sudoku.core.model.Board
import com.gailswintha.sudoku.core.model.Board.Companion.COLUMN_LENGTH
import com.gailswintha.sudoku.core.model.Board.Companion.FIRST
import com.gailswintha.sudoku.core.model.Board.Companion.ROW_LENGTH
import com.gailswintha.sudoku.core.model.Board.Companion.VALUE_RANGE
import com.gailswintha.sudoku.core.model.Board.Companion.columnPositions
import com.gailswintha.sudoku.core.model.Board.Companion.isLast
import com.gailswintha.sudoku.core.model.Board.Companion.next
import com.gailswintha.sudoku.core.model.Board.Companion.rowPositions
import com.gailswintha.sudoku.core.model.Board.Companion.sectionCenter
import com.gailswintha.sudoku.core.model.Position

class SimpleDeduction : Strategy {
    override val order = 0

    override fun complete(board: Board): Board = complete(Board(board), Position.FIRST)

    private tailrec fun complete(board: Board, position: Position): Board = when {
        completeRowsWithOnlyOneMissingValue(board) ||
                completeColumnsWithOnlyOneMissingValue(board) ||
                completeSectionsWithOnlyOneMissingValue(board) ||
                completeFieldIfPossible(board, position) -> complete(board, Position.FIRST)
        position.isLast -> board
        else -> complete(board, position.next())
    }

    private fun Board.getPossibleColumnsInSection(position: Position) = Board.SECTION_POSITIONS[position.sectionCenter]
            ?.filter { !isSet(it) }
            ?.map(Position::column)
            ?.toSet()
            ?: emptySet()

    private fun Board.getPossibleRowsInSection(position: Position) = Board.SECTION_POSITIONS[position.sectionCenter]
            ?.filter { !isSet(it) }
            ?.map(Position::row)
            ?.toSet()
            ?: emptySet()

    private fun Board.getPossiblePositionsInSection(position: Position, newValue: Int) =
            (Board.SECTION_POSITIONS[position.sectionCenter] ?: emptyList())
                    .filter { position ->
                        !isSet(position) &&
                            position.rowPositions.none { this[it] == newValue } &&
                            position.columnPositions.none { this[it] == newValue }
                }

    private fun Board.getImpossibleColumnsInOtherSections(position: Position, newValue: Int): Set<Int> {
        val otherSectionCenters = Board.SECTION_CENTERS.filter {
            it.column == position.sectionCenter.column && it.row != position.sectionCenter.row
        }
        val allOtherPositions = otherSectionCenters.flatMap { Board.SECTION_POSITIONS[it] ?: emptyList() }
        val columns = allOtherPositions.groupBy { it.column }

        val columnsWithoutNewValue = columns.filter { (_, positions) -> positions.none { this[it] == newValue } }
        val possibleColumns = columnsWithoutNewValue.filter { (_, positions) ->
            positions.any { position ->
                !isSet(position) || (isSet(position) && (0 until COLUMN_LENGTH).map { Position(position.row, it) }.none { this[it] == newValue })
            }
        }
        return columns.keys - possibleColumns.keys
    }

    private fun Board.getImpossibleRowsInOtherSections(position: Position, newValue: Int): Set<Int> {
        val otherSectionCenters = Board.SECTION_CENTERS.filter {
            it.row == position.sectionCenter.row && it.column != position.sectionCenter.column
        }
        val allOtherPositions = otherSectionCenters.flatMap { Board.SECTION_POSITIONS[it] ?: emptyList() }
        val rows = allOtherPositions.groupBy { it.row }

        val rowsWithoutNewValue = rows.filter { (_, positions) -> positions.none { this[it] == newValue } }
        val possibleRows = rowsWithoutNewValue.filter { (_, positions) ->
            positions.any { position ->
                !isSet(position) || (isSet(position) && (0 until ROW_LENGTH).map { Position(it, position.column) }.none { this[it] == newValue })
            }
        }
        return rows.keys - possibleRows.keys
    }

    private fun completeFieldIfPossible(board: Board, position: Position): Boolean = if(board.isSet(position)) {
        false
    } else {
        (VALUE_RANGE - board.row(position.row) - board.column(position.column) - board.section(position)).map { newValue ->
            val possibleColumnsTotal = board.getPossibleColumnsInSection(position) - board.getImpossibleColumnsInOtherSections(position, newValue)
            val possibleRowsTotal = board.getPossibleRowsInSection(position) - board.getImpossibleRowsInOtherSections(position, newValue)
            val possiblePositionsInSection = board.getPossiblePositionsInSection(position, newValue)

            when {
                possiblePositionsInSection.size == 1 -> {
                    board[possiblePositionsInSection.first()] = newValue
                    true
                }
                possibleColumnsTotal.size == 1 && possibleRowsTotal.size == 1 -> {
                    board[Position(possibleRowsTotal.first(), possibleColumnsTotal.first())] = newValue
                    true
                }
                else -> false
            }
        }.any { it }
    }

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
        val sectionPositions = Board.SECTION_POSITIONS[sectionCenter] ?: emptyList()

        tailrec fun recFn(positionIndex: Int): Boolean = when {
            positionIndex == sectionPositions.size -> false
            !isSet(sectionPositions[positionIndex]) -> { this[sectionPositions[positionIndex]] = value; true }
            else -> recFn(positionIndex + 1)
        }
        return recFn(0)
    }
}