package com.gailswintha.sudoku.core.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `Board is constructed successfully`() {
        assertThat(Board()).isNotNull
    }

    @Test
    fun `Board has correct dimensions`() {
        assertThat(Board().rowLength).isEqualTo(9)
        assertThat(Board().columnLength).isEqualTo(9)
    }

    @Test
    fun `Board has correct initial values`() {
        val board = Board()
        for(row in 0..8) {
            for(column in 0..8) {
                assertThat(board[Position(row, column)]).isEqualTo(-1)
            }
        }
    }

    @Test
    fun `set value sets correct value`() {
        val board = Board()
        val position = Position(4, 3)
        board[position] = 1
        assertThat(board[position]).isEqualTo(1)
        board[position] = 7
        assertThat(board[position]).isEqualTo(7)
        board[position] = 9
        assertThat(board[position]).isEqualTo(9)
    }

    @Test
    fun `set value throws IllegalArgumentException in case value is too small`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Board()[Position(0, 0)] = 0 }
    }

    @Test
    fun `set value throws IllegalArgumentException in case value is too large`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Board()[Position(0, 0)] = 10 }
    }

    @Test
    fun `unset value sets correct value`() {
        val board = Board()
        val position = Position(4, 3)
        board[position] = 1
        assertThat(board.isSet(position)).isTrue
        board.unset(position)
        assertThat(board.isSet(position)).isFalse
    }
}