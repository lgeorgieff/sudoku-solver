package com.gailswintha.sudoku.solver.strategy

import com.gailswintha.sudoku.core.io.loadFromArray
import com.gailswintha.sudoku.core.model.Board
import com.gailswintha.sudoku.core.model.Board.Companion.X
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SimpleDeductionTest {
    @Test
    fun `board is completed successfully`() {
        val incompleteBoard = Board.loadFromArray(
                arrayOf(
                        arrayOf(9, 5, 6, 4, X, X, X, X, X),
                        arrayOf(X, 1, X, X, 9, 5, 6, X, X),
                        arrayOf(X, 7, X, X, X, X, X, X, X),
                        arrayOf(X, 2, 8, X, 4, X, 1, X, 9),
                        arrayOf(X, 3, X, 2, X, 6, X, 5, X),
                        arrayOf(7, X, 5, X, 3, X, 8, 2, X),
                        arrayOf(X, X, X, X, X, X, X, 7, X),
                        arrayOf(X, X, 4, 7, 5, X, X, 6, X),
                        arrayOf(X, X, X, X, X, 4, 5, 1, 2)
                )
        )

        val completedBoard = Board.loadFromArray(
                arrayOf(
                    arrayOf(9, 5, 6, 4, 7, 2, 3, 8, 1),
                    arrayOf(8, 1, 2, 3, 9, 5, 6, 4, 7),
                    arrayOf(4, 7, 3, 6, 1, 8, 2, 9, 5),
                    arrayOf(6, 2, 8, 5, 4, 7, 1, 3, 9),
                    arrayOf(1, 3, 9, 2, 8, 6, 7, 5, 4),
                    arrayOf(7, 4, 5, 1, 3, 9, 8, 2, 6),
                    arrayOf(5, 6, 1, 9, 2, 3, 4, 7, 8),
                    arrayOf(2, 8, 4, 7, 5, 1, 9, 6, 3),
                    arrayOf(3, 9, 7, 8, 6, 4, 5, 1, 2)
            )
        )
        assertThat(SimpleDeduction().complete(incompleteBoard)).isEqualTo(completedBoard)
    }
}