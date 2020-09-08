package com.gailswintha.sudoku.core.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MatrixTest {
    private val matrix_1x1_value = 99
    private lateinit var matrix_1x1: Matrix<Int>
    private lateinit var matrix_5x5: Matrix<Int>

    @BeforeEach
    fun setUp() {
        matrix_1x1 = Matrix(1, 1) { matrix_1x1_value }
        matrix_5x5 = Matrix(5, 5){ (row, col) -> row * col }
    }

    @Test
    fun `Matrix is constructed successfully`() {
        assertThat(Matrix(1, 1){ 1 }).isNotNull
    }

    @Test
    fun `Constructor throws IllegalArgumentException in case of rowLength being smaller than 1`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Matrix(0, 1) { 1 } }
    }

    @Test
    fun `Constructor throws IllegalArgumentException in case of columnLength being smaller than 1`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Matrix(1, 0) { 1 } }
    }

    @Test
    fun `get operator returns correct value`() {
        assertThat(matrix_1x1[Position(0, 0)]).isEqualTo(matrix_1x1_value)
    }

    @Test
    fun `get operator throws InvalidPosition in case of row index being greater than or equal rowLength`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { matrix_1x1[Position(1, 0)] }
    }

    @Test
    fun `get operator throws InvalidPosition in case of column index being greater than or equal columnIndex`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { matrix_1x1[Position(0, 1)] }
    }

    @Test
    fun `set operator sets correct value`() {
        matrix_1x1[Position(0, 0)] = 88
        assertThat(matrix_1x1[Position(0, 0)]).isEqualTo(88)
    }

    @Test
    fun `set operator throws InvalidPosition in case of row index being greater than or equal rowLength`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { matrix_1x1[Position(0, 1)] = 123 }
    }

    @Test
    fun `set operator throws InvalidPosition in case of column index being greater than or equal columnLength`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { matrix_1x1[Position(1, 0)] = 123 }
    }

    @Test
    fun `row() returns Array with correct values`() {
        assertThat(matrix_5x5.row(2)).isEqualTo(listOf(0, 2, 4, 6, 8))
    }

    @Test
    fun `row() throws IndexOutOfBoundsException in case of row index being smaller than 0`() {
        assertThatExceptionOfType(IndexOutOfBoundsException::class.java).isThrownBy { matrix_1x1.row(-1) }
    }

    @Test
    fun `row() throws IndexOutOfBoundsException in case of row index being greater than or equal rowLength`() {
        assertThatExceptionOfType(IndexOutOfBoundsException::class.java).isThrownBy { matrix_1x1.row(1) }
    }

    @Test
    fun `column() returns Array with correct values`() {
        assertThat(matrix_5x5.column(3)).isEqualTo(listOf(0, 3, 6, 9, 12))
    }

    @Test
    fun `column() throws IndexOutOfBoundsException in case of column index being smaller than 0`() {
        assertThatExceptionOfType(IndexOutOfBoundsException::class.java).isThrownBy { matrix_1x1.column(-1) }
    }

    @Test
    fun `column() throws IndexOutOfBoundsException in case of column index being greater than or equal columnLength`() {
        assertThatExceptionOfType(IndexOutOfBoundsException::class.java).isThrownBy { matrix_1x1.column(1) }
    }

    @Test
    fun `toString() returns correct value`() {
        assertThat(matrix_5x5.toString()).isEqualTo(
                """
                    0 0 0 0 0
                    0 1 2 3 4
                    0 2 4 6 8
                    0 3 6 9 12
                    0 4 8 12 16
                """.trimIndent()
        )
    }
}
