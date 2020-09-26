package com.gailswintha.sudoku.solver.strategy

import com.gailswintha.sudoku.core.io.loadFromArray
import com.gailswintha.sudoku.core.model.Board
import com.gailswintha.sudoku.core.model.Board.Companion.X
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeductionTest {
    private lateinit var boardSingleMissingElementInSection: Board
    private lateinit var boardSingleMissingElementInRow: Board
    private lateinit var boardSingleMissingElementInColumn: Board

    @BeforeEach
    fun setUp() {
        boardSingleMissingElementInSection = Board.loadFromArray(
                arrayOf(
                        arrayOf(1, 2, 3, X, X, X, X, X, X),
                        arrayOf(7, X, 9, X, X, X, X, X, X),
                        arrayOf(4, 5, 6, X, X, X, X, X, X),
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(X, 3, 4, X, X, X, 8, 9, 1),
                        arrayOf(5, 6, 7, X, X, X, 2, 3, X),
                        arrayOf(8, 9, 1, X, X, X, 5, 6, 7)
                )
        )

        boardSingleMissingElementInRow = Board.loadFromArray(
                arrayOf(
                        arrayOf(X, 2, 3, 4, 5, 6, 7, 8, 9),
                        arrayOf(7, 8, 9, 1, 2, 3, 4, 5, X),
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(9, 1, 2, 3, 4, 5, 6, 7, 8),
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(3, 4, 5, 6, 7, 8, 9, 1, X),
                        arrayOf(2, X, 4, 5, 6, 7, 8, X, 1),
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(X, X, X, X, X, X, X, 6, 7)
                )
        )

        boardSingleMissingElementInColumn = Board.loadFromArray(
                arrayOf(
                        arrayOf(X, 2, X, X, 5, 6, X, X, 9),
                        arrayOf(7, 8, X, X, 2, X, X, X, 6),
                        arrayOf(4, 5, X, X, 8, 9, X, X, 3),
                        arrayOf(9, 1, X, X, X, 5, X, X, 8),
                        arrayOf(6, 7, X, X, 1, 2, 3, X, 5),
                        arrayOf(3, 4, X, X, 7, 8, X, X, 2),
                        arrayOf(2, 3, X, X, 6, 7, X, X, 1),
                        arrayOf(5, 6, X, X, 9, X, X, X, 4),
                        arrayOf(8, X, X, X, 3, 4, X, X, 7)
                )
        )
    }

    @Test
    fun `rows with single missing item are completed`() {
        val expected = Board.loadFromArray(
                arrayOf(
                        arrayOf(1, 2, 3, X, X, X, X, X, X),
                        arrayOf(7, 8, 9, X, X, X, X, X, X),
                        arrayOf(4, 5, 6, X, X, X, X, X, X),
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(2, 3, 4, X, X, X, 8, 9, 1),
                        arrayOf(5, 6, 7, X, X, X, 2, 3, 4),
                        arrayOf(8, 9, 1, X, X, X, 5, 6, 7)
                )
        )
        assertThat(Deduction().complete(boardSingleMissingElementInSection)).isEqualTo(expected)
    }

    @Test
    fun `columns with single missing item are completed`() {
        val expected = Board.loadFromArray(
                arrayOf(
                        arrayOf(1, 2, X, X, 5, 6, X, X, 9),
                        arrayOf(7, 8, X, X, 2, X, X, X, 6),
                        arrayOf(4, 5, X, X, 8, 9, X, X, 3),
                        arrayOf(9, 1, X, X, 4, 5, X, X, 8),
                        arrayOf(6, 7, X, X, 1, 2, 3, X, 5),
                        arrayOf(3, 4, X, X, 7, 8, X, X, 2),
                        arrayOf(2, 3, X, X, 6, 7, X, X, 1),
                        arrayOf(5, 6, X, X, 9, X, X, X, 4),
                        arrayOf(8, 9, X, X, 3, 4, X, X, 7)
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
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(9, 1, 2, 3, 4, 5, 6, 7, 8),
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(3, 4, 5, 6, 7, 8, 9, 1, 2),
                        arrayOf(2, X, 4, 5, 6, 7, 8, X, 1),
                        arrayOf(X, X, X, X, X, X, X, X, X),
                        arrayOf(X, X, X, X, X, X, X, 6, 7)
                )
        )
        assertThat(Deduction().complete(boardSingleMissingElementInRow)).isEqualTo(expected)
    }
}