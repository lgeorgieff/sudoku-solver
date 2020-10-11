package com.gailswintha.sudoku.solver.strategy

import com.gailswintha.sudoku.core.io.loadFromArray
import com.gailswintha.sudoku.core.model.Board
import com.gailswintha.sudoku.core.model.Board.Companion.X
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BacktrackingTest {
    @Test
    fun `board is completed successfully`() {
        val incompleteBoard = Board.loadFromArray(
                arrayOf(
                        arrayOf(X, X, X, 4, X, 6, X, X, 9),
                        arrayOf(X, X, 3, X, X, X, X, 8, 1),
                        arrayOf(X, X, X, 9, X, X, 3, 2, X),
                        arrayOf(2, X, X, 5, X, X, X, X, X),
                        arrayOf(X, 5, 1, X, X, X, X, X, X),
                        arrayOf(X, X, X, X, 2, 4, X, X, X),
                        arrayOf(X, 3, X, X, X, X, 8, X, X),
                        arrayOf(X, X, 6, X, X, 7, X, X, X),
                        arrayOf(X, 9, X, X, 1, X, 7, 3, X)
                )
        )

        val completedBoard = Board.loadFromArray(
                arrayOf(
                        arrayOf(1, 2, 8, 4, 3, 6, 5, 7, 9),
                        arrayOf(9, 4, 3, 2, 7, 5, 6, 8, 1),
                        arrayOf(6, 7, 5, 9, 8, 1, 3, 2, 4),
                        arrayOf(2, 8, 9, 5, 6, 3, 1, 4, 7),
                        arrayOf(4, 5, 1, 7, 9, 8, 2, 6, 3),
                        arrayOf(3, 6, 7, 1, 2, 4, 9, 5, 8),
                        arrayOf(7, 3, 2, 6, 4, 9, 8, 1, 5),
                        arrayOf(8, 1, 6, 3, 5, 7, 4, 9, 2),
                        arrayOf(5, 9, 4, 8, 1, 2, 7, 3, 6)
                )
        )
        Assertions.assertThat(Backtracking().complete(incompleteBoard)).isEqualTo(completedBoard)
    }
}