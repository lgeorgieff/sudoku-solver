package com.gailswintha.sudoku.core.model

class Board : Matrix<Int>(ROW_LENGTH, COLUMN_LENGTH, { NO_VALUE }) {
    companion object {
        fun isValidValue(value: Int) = value in VALUE_RANGE
        const val ROW_LENGTH = 9
        const val COLUMN_LENGTH = 9
        const val NO_VALUE = -1
        val VALUE_RANGE = 1..9
    }

    fun isSet(position: Position) = this[position] != NO_VALUE

    fun unset(position: Position) {
        super.set(position, NO_VALUE)
    }

    override fun set(position: Position, value: Int) {
        if(!isValidValue(value)) throw IllegalArgumentException(""""value" must be in the range 1..9, but is $value""")
        super.set(position, value)
    }

    fun getAllSectionPositionsFor(position: Position): List<Position> {
        checkPosition(position)
        val firstRowIndex = position.row - (position.row % 3)
        val firstColumnIndex = position.column - (position.column % 3)

        return (firstRowIndex..(firstRowIndex + 2)).flatMap { rowIndex ->
            (firstColumnIndex..(firstColumnIndex + 2)).map { columnIndex -> Position(rowIndex, columnIndex) }
        }
    }

    fun section(position: Position) = getAllSectionPositionsFor(position).map { this[it] }
}
