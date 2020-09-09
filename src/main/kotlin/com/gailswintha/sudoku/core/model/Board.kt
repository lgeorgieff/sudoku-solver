package com.gailswintha.sudoku.core.model

class Board : Matrix<Int>(ROW_LENGTH, COLUMN_LENGTH, { NO_VALUE }) {
    companion object {
        fun isValidValue(value: Int) = value in VALUE_RANGE
    }

    fun isSet(position: Position) = this[position] != NO_VALUE

    fun unset(position: Position) {
        super.set(position, NO_VALUE)
    }

    override fun set(position: Position, value: Int) {
        if(!isValidValue(value)) throw IllegalArgumentException(""""value" must be in the range 1..9, but is $value""")
        super.set(position, value)
    }
}
