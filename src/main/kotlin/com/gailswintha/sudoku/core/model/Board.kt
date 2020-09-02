package com.gailswintha.sudoku.core.model

import java.nio.file.Path

class Board : Matrix<Int>(ROW_LENGTH, COLUMN_LENGTH, { -1 }) {
    companion object {
        fun isValidValue(value: Int) = value in 1..9

        fun loadFromJsonFile(filePath: Path): Board {
            TODO("Implement")
        }
    }

    fun isSet(position: Position) = this[position] != -1

    override fun set(position: Position, value: Int) {
        if(isValidValue(value)) throw IllegalArgumentException(""""value" must be in the range 1..9, but is $value""")
        super.set(position, value)
    }
}