package com.gailswintha.sudoku.core.model

data class Position(val row: Int, val column: Int) {
    init {
        if(row < 0) throw IllegalArgumentException(""""row" must be >= 0, but is "$row".""")
        if(column < 0) throw IllegalArgumentException(""""column" must be >= 0, but is "$column".""")
    }

    companion object

    val nextRow get() = Position(row + 1, column)
    val nextColumn get() = Position(row, column + 1)
    val previousRow: Position
        get() {
        if(row == 0) throw IllegalOperation("""The position "$this" has no previous row.""")
        return Position(row - 1, column)
    }
    val previousColumn: Position
        get() {
        if(column == 0) throw IllegalOperation("""The position "$this" has no previous column.""")
        return Position(row, column - 1)
    }
}