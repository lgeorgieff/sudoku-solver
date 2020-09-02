package com.gailswintha.sudoku.core.model

class Possibilities : Matrix<Set<Int>>(ROW_LENGTH, COLUMN_LENGTH, { mutableSetOf() }) {
    override fun set(position: Position, value: Set<Int>) {
        val invalidValues = value.filterInvalid()
        if(invalidValues.isNotEmpty())
            throw IllegalArgumentException(""""value" must be a list of ints in the range 1..9, but it contains $invalidValues""")
        super.set(position, value)
    }

    fun addPossibility(position: Position, possibility: Int) = (this[position] as MutableSet<Int>).add(possibility)

    fun removePossibility(position: Position, possibility: Int) = (this[position] as MutableSet<Int>).remove(possibility)

    fun hasPossibility(position: Position, possibility: Int) = this[position].contains(possibility)

    private fun Set<Int>.filterInvalid(): List<Int> = this.filter { !Board.isValidValue(it) }
}