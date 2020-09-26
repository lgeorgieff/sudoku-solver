package com.gailswintha.sudoku.core.model

open class Matrix<T>(val rowLength: Int, val columnLength: Int, val initFn: (Position) -> T) {
    init {
        if (rowLength <= 0) throw IllegalArgumentException(""""rowLength" must be > 0, but is "$rowLength".""")
        if (columnLength <= 0) throw IllegalArgumentException(""""columnLength" must be > 0, but is "$columnLength".""")
    }

    @Suppress("UNCHECKED_CAST")
    val data = Array(rowLength) { row ->
        Array<Any?>(columnLength) { column ->
            val position = Position(row, column)
            initFn(position)
        }
    } as Array<Array<T>>

    protected fun assertPosition(position: Position) {
        if(position.row >= rowLength) throw InvalidPosition(position, rowLength, columnLength)
        if(position.column >= columnLength) throw InvalidPosition(position, rowLength, columnLength)
    }

    open operator fun get(position: Position): T {
        assertPosition(position)
        return data[position.row][position.column]
    }

    open operator fun set(position: Position, value: T) {
        assertPosition(position)
        data[position.row][position.column] = value
    }

    fun row(rowIndex: Int): List<T> {
        if(rowIndex >= rowLength || rowIndex < 0) throw IndexOutOfBoundsException("rowIndex must be in range 0..${rowLength - 1}, but is $rowIndex")
        return data[rowIndex].map { it }
    }

    @Suppress("UNCHECKED_CAST")
    fun column(columnIndex: Int): List<T> {
        if(columnIndex >= rowLength || columnIndex < 0)
            throw IndexOutOfBoundsException("columnIndex must be in range 0..${columnLength - 1}, but is $columnIndex")
        return data.map { it[columnIndex] }
    }

    override fun toString() = data.joinToString(separator = "\n") { row ->
        row.joinToString(separator = " ")
    }
}
