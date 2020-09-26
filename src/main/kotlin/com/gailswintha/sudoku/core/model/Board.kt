package com.gailswintha.sudoku.core.model

class Board() : Matrix<Int>(ROW_LENGTH, COLUMN_LENGTH, { NO_VALUE }) {
    constructor(source: Board) : this() {
        for(rowIndex in 0 until source.rowLength) {
            for (columnIndex in 0 until source.columnLength) {
                val position = Position(rowIndex, columnIndex)
                if(source.isSet(position)) this[position] = source[position]
            }
        }
    }

    companion object {
        fun isValidValue(value: Int) = value in VALUE_RANGE
        const val ROW_LENGTH = 9
        const val COLUMN_LENGTH = 9
        const val SECTION_LENGTH = 9
        const val NO_VALUE = -1
        val VALUE_RANGE = 1..9

        val SECTION_CENTERS =
            (1..7 step 3).flatMap { rowIndex ->
                (1..7 step 3).map { columnIndex ->
                    Position(rowIndex, columnIndex)
                }
            }
        val SECTION_POSITIONS = (0 until ROW_LENGTH).flatMap { rowIndex ->
            (0 until COLUMN_LENGTH).map {
                columnIndex -> Position(rowIndex, columnIndex)
            }
        }.groupBy { (rowIndex, columnIndex) ->
            Position((rowIndex / 3) * 3 + 1, (columnIndex / 3) * 3 + 1)
        }

        val FIRST_POSITION = Position(0, 0)
        val LAST_POSITION = Position(ROW_LENGTH - 1, COLUMN_LENGTH - 1)

        fun Position.next() = when {
            row >= ROW_LENGTH || column >= COLUMN_LENGTH -> throw InvalidPosition(this, ROW_LENGTH, COLUMN_LENGTH)
            this == Board.LAST_POSITION -> throw InvalidPosition(Position(row, column + 1), ROW_LENGTH, COLUMN_LENGTH)
            column == COLUMN_LENGTH - 1 -> Position(row + 1, 0)
            else -> Position(row, column + 1)
        }
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
        assertPosition(position)
        val firstRowIndex = position.row - (position.row % 3)
        val firstColumnIndex = position.column - (position.column % 3)

        return (firstRowIndex..(firstRowIndex + 2)).flatMap { rowIndex ->
            (firstColumnIndex..(firstColumnIndex + 2)).map { columnIndex -> Position(rowIndex, columnIndex) }
        }
    }

    fun section(position: Position) = getAllSectionPositionsFor(position).map { this[it] }

    fun isRowValid(rowIndex: Int): Boolean = with(row(rowIndex).toSet()) {
        !this.contains(NO_VALUE) && this.size == COLUMN_LENGTH
    }

    fun isColumnValid(columnIndex: Int): Boolean = with(column(columnIndex).toSet()) {
        !this.contains(NO_VALUE) && this.size == ROW_LENGTH
    }

    fun isSectionValid(position: Position): Boolean = with(section(position).toSet()) {
        !this.contains(-1) && this.size == SECTION_LENGTH
    }

    val isValid: Boolean
        get() {
            for(rowIndex in 1 until ROW_LENGTH) if(!this.isRowValid(rowIndex)) return false
            for(columnIndex in 1 until COLUMN_LENGTH) if(!this.isColumnValid(columnIndex)) return false
            for(sectionCenter in SECTION_CENTERS) if(!this.isSectionValid(sectionCenter)) return false
            return true
        }

    val isComplete: Boolean
        get() {
            return (0 until ROW_LENGTH).flatMap { rowIndex ->
                (0 until COLUMN_LENGTH).map { columnIndex ->
                    this[Position(rowIndex, columnIndex)] == NO_VALUE
                }
            }.all { !it }
        }

    override fun equals(other: Any?): Boolean {
        if(other !is Board) return false

        for(rowIndex in 0 until ROW_LENGTH) {
            for(columnIndex in 0 until COLUMN_LENGTH) {
                val position = Position(rowIndex, columnIndex)
                if(this[position] != other[position]) return false
            }
        }

        return true
    }
}
