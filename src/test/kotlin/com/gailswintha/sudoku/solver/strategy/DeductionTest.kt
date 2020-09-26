package com.gailswintha.sudoku.solver.strategy

import com.gailswintha.sudoku.core.io.loadFromArray
import com.gailswintha.sudoku.core.model.Board
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeductionTest {
    private lateinit var boardSingleMissingElementInSection: Board
    private lateinit var boardSingleMissingElementInRow: Board
    private lateinit var boardSingleMissingElementInColumn: Board
    private val x = -1

    @BeforeEach
    fun setUp() {
        boardSingleMissingElementInSection = Board.loadFromArray(
                arrayOf(
                        arrayOf(1, 2, 3, x, x, x, x, x, x),
                        arrayOf(7, x, 9, x, x, x, x, x, x),
                        arrayOf(4, 5, 6, x, x, x, x, x, x),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(x, 3, 4, x, x, x, 8, 9, 1),
                        arrayOf(5, 6, 7, x, x, x, 2, 3, x),
                        arrayOf(8, 9, 1, x, x, x, 5, 6, 7)
                )
        )

        boardSingleMissingElementInRow = Board.loadFromArray(
                arrayOf(
                        arrayOf(x, 2, 3, 4, 5, 6, 7, 8, 9),
                        arrayOf(7, 8, 9, 1, 2, 3, 4, 5, x),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(9, 1, 2, 3, 4, 5, 6, 7, 8),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(3, 4, 5, 6, 7, 8, 9, 1, x),
                        arrayOf(2, x, 4, 5, 6, 7, 8, x, 1),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(x, x, x, x, x, x, x, 6, 7)
                )
        )

        boardSingleMissingElementInColumn = Board.loadFromArray(
                arrayOf(
                        arrayOf(x, 2, x, x, 5, 6, x, x, 9),
                        arrayOf(7, 8, x, x, 2, x, x, x, 6),
                        arrayOf(4, 5, x, x, 8, 9, x, x, 3),
                        arrayOf(9, 1, x, x, x, 5, x, x, 8),
                        arrayOf(6, 7, x, x, 1, 2, 3, x, 5),
                        arrayOf(3, 4, x, x, 7, 8, x, x, 2),
                        arrayOf(2, 3, x, x, 6, 7, x, x, 1),
                        arrayOf(5, 6, x, x, 9, x, x, x, 4),
                        arrayOf(8, x, x, x, 3, 4, x, x, 7)
                )
        )
    }

    @Test
    fun `rows with single missing item are completed`() {
        val expected = Board.loadFromArray(
                arrayOf(
                        arrayOf(1, 2, 3, x, x, x, x, x, x),
                        arrayOf(7, 8, 9, x, x, x, x, x, x),
                        arrayOf(4, 5, 6, x, x, x, x, x, x),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(2, 3, 4, x, x, x, 8, 9, 1),
                        arrayOf(5, 6, 7, x, x, x, 2, 3, 4),
                        arrayOf(8, 9, 1, x, x, x, 5, 6, 7)
                )
        )
        assertThat(Deduction().complete(boardSingleMissingElementInSection)).isEqualTo(expected)
    }

    @Test
    fun `columns with single missing item are completed`() {
        val expected = Board.loadFromArray(
                arrayOf(
                        arrayOf(1, 2, x, x, 5, 6, x, x, 9),
                        arrayOf(7, 8, x, x, 2, x, x, x, 6),
                        arrayOf(4, 5, x, x, 8, 9, x, x, 3),
                        arrayOf(9, 1, x, x, 4, 5, x, x, 8),
                        arrayOf(6, 7, x, x, 1, 2, 3, x, 5),
                        arrayOf(3, 4, x, x, 7, 8, x, x, 2),
                        arrayOf(2, 3, x, x, 6, 7, x, x, 1),
                        arrayOf(5, 6, x, x, 9, x, x, x, 4),
                        arrayOf(8, 9, x, x, 3, 4, x, x, 7)
                )
        )
        assertThat(Deduction().complete(boardSingleMissingElementInColumn)).isEqualTo(expected)
    }

    @Test
    fun `sections with single missing item are completed`() {
        val expected = Board.loadFromArray(
                arrayOf(
                        arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
                        arrayOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(9, 1, 2, 3, 4, 5, 6, 7, 8),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(3, 4, 5, 6, 7, 8, 9, 1, 2),
                        arrayOf(2, x, 4, 5, 6, 7, 8, x, 1),
                        arrayOf(x, x, x, x, x, x, x, x, x),
                        arrayOf(x, x, x, x, x, x, x, 6, 7)
                )
        )
        assertThat(Deduction().complete(boardSingleMissingElementInRow)).isEqualTo(expected)
    }
}