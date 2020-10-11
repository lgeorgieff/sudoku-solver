package com.gailswintha.sudoku.core.model

class InvalidPosition(row: Int, column: Int, rowLength: Int? = null, columnLength: Int? = null) : IndexOutOfBoundsException(
    when {
        rowLength == null && columnLength == null -> """The position "(row=$row, column=$column)" is invalid!"""
        rowLength == null -> """The position "(row=$row, column=$column)" is invalid! "rowLength": $rowLength."""
        columnLength == null -> """The position "(row=$row, column=$column)" is invalid! "columnLength": $columnLength."""
        else -> """The position "(row=$row, column=$column)" is invalid! "rowLength": $rowLength, "columnLength": $columnLength."""
    }
) {
    constructor(position: Position, rowLength: Int? = null, columnLength: Int? = null): this(position.row, position.column, rowLength, columnLength)
}