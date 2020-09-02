package com.gailswintha.sudoku.core.model

class InvalidPosition(position: Position, rowLength: Int? = null, columnLength: Int? = null) : IndexOutOfBoundsException(
    when {
        rowLength == null && columnLength == null -> """The position "$position" is invalid!"""
        rowLength == null -> """The position "$position" is invalid! "rowLength": $rowLength."""
        columnLength == null -> """The position "$position" is invalid! "columnLength": $columnLength."""
        else -> """The position "$position" is invalid! "rowLength": $rowLength, "columnLength": $columnLength."""
    }
)