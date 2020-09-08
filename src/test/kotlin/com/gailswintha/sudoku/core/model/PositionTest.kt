package com.gailswintha.sudoku.core.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun `Position is constructed correctly`() {
        assertThat(Position(1, 2)).isNotNull
    }

    @Test
    fun `Constructor fails if row is smaller than 0`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Position(-1, 2) }
    }

    @Test
    fun `Constructor fails if column is smaller than 0`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Position(1, -1) }
    }

    @Test
    fun `Constructor fails if row and column is smaller than 0`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Position(-1, -1) }
    }

    @Test
    fun `row getter returns correct value`() {
        assertThat(Position(1, 2).row).isEqualTo(1)
    }

    @Test
    fun `column getter returns correct value`() {
        assertThat(Position(1, 2).column).isEqualTo(2)
    }

    @Test
    fun `nextRow getter returns correct value`() {
        assertThat(Position(1, 2).nextRow).isEqualTo(Position(2, 2))
    }

    @Test
    fun `nextColumn getter returns correct value`() {
        assertThat(Position(1, 2).nextColumn).isEqualTo(Position(1, 3))
    }

    @Test
    fun `previousRow getter returns correct value`() {
        assertThat(Position(1, 2).previousRow).isEqualTo(Position(0, 2))
    }

    @Test
    fun `previousColumn getter returns correct value`() {
        assertThat(Position(1, 2).previousColumn).isEqualTo(Position(1, 1))
    }

    @Test
    fun `previousRow getter throws in case current row value is 0`() {
        assertThatExceptionOfType(IllegalOperation::class.java).isThrownBy { Position(0, 2).previousRow }
    }

    @Test
    fun `previousColumn getter throws in case current column value is 0`() {
        assertThatExceptionOfType(IllegalOperation::class.java).isThrownBy { Position(1, 0).previousColumn }
    }
}
