package com.gailswintha.sudoku.core.model

import com.gailswintha.sudoku.core.io.loadFromArray
import com.gailswintha.sudoku.core.model.Board.Companion.next
import com.gailswintha.sudoku.data.BOARD_1_COMPLETE
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var validBoard: Board

    @BeforeEach
    fun setUp() {
        validBoard = Board.loadFromArray(BOARD_1_COMPLETE)
    }

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

    @Test
    fun `getAllSectionPositionsFor() throws InvalidPosition in case of row index is equal to rowLength`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { Board().getAllSectionPositionsFor(Position(Board.ROW_LENGTH, 0)) }
    }

    @Test
    fun `getAllSectionPositionsFor() throws InvalidPosition in case of row index is greater than to rowLength`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { Board().getAllSectionPositionsFor(Position(Board.ROW_LENGTH + 1, 0)) }
    }

    @Test
    fun `getAllSectionPositionsFor() throws InvalidPosition in case of column index is equal to columnLength`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { Board().getAllSectionPositionsFor(Position(0, Board.COLUMN_LENGTH)) }
    }

    @Test
    fun `getAllSectionPositionsFor() throws InvalidPosition in case of column index is greater than to columnLength`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { Board().getAllSectionPositionsFor(Position(0, Board.COLUMN_LENGTH + 1)) }
    }

    @Test
    fun `getAllSectionPositionsFor() returns valid positions for section 1x1`() {
        val positions = setOf(
            Position(0, 0), Position(0, 1), Position(0, 2),
            Position(1, 0), Position(1, 1), Position(1, 2),
            Position(2, 0), Position(2, 1), Position(2, 2)
        )

        positions.forEach { position ->
            assertThat(Board().getAllSectionPositionsFor(position).toSet()).isEqualTo(positions)
        }
    }

    @Test
    fun `getAllSectionPositionsFor() returns valid positions for section 1x2`() {
        val positions = setOf(
            Position(0, 3), Position(0, 4), Position(0, 5),
            Position(1, 3), Position(1, 4), Position(1, 5),
            Position(2, 3), Position(2, 4), Position(2, 5)
        )

        positions.forEach { position ->
            assertThat(Board().getAllSectionPositionsFor(position).toSet()).isEqualTo(positions)
        }
    }

    @Test
    fun `getAllSectionPositionsFor() returns valid positions for section 1x3`() {
        val positions = setOf(
            Position(0, 6), Position(0, 7), Position(0, 8),
            Position(1, 6), Position(1, 7), Position(1, 8),
            Position(2, 6), Position(2, 7), Position(2, 8)
        )

        positions.forEach { position ->
            assertThat(Board().getAllSectionPositionsFor(position).toSet()).isEqualTo(positions)
        }
    }

    @Test
    fun `getAllSectionPositionsFor() returns valid positions for section 2x1`() {
        val positions = setOf(
            Position(3, 0), Position(3, 1), Position(3, 2),
            Position(4, 0), Position(4, 1), Position(4, 2),
            Position(5, 0), Position(5, 1), Position(5, 2)
        )

        positions.forEach { position ->
            assertThat(Board().getAllSectionPositionsFor(position).toSet()).isEqualTo(positions)
        }
    }

    @Test
    fun `getAllSectionPositionsFor() returns valid positions for section 2x2`() {
        val positions = setOf(
            Position(3, 3), Position(3, 4), Position(3, 5),
            Position(4, 3), Position(4, 4), Position(4, 5),
            Position(5, 3), Position(5, 4), Position(5, 5)
        )

        positions.forEach { position ->
            assertThat(Board().getAllSectionPositionsFor(position).toSet()).isEqualTo(positions)
        }
    }

    @Test
    fun `getAllSectionPositionsFor() returns valid positions for section 2x3`() {
        val positions = setOf(
            Position(3, 6), Position(3, 7), Position(3, 8),
            Position(4, 6), Position(4, 7), Position(4, 8),
            Position(5, 6), Position(5, 7), Position(5, 8)
        )

        positions.forEach { position ->
            assertThat(Board().getAllSectionPositionsFor(position).toSet()).isEqualTo(positions)
        }
    }

    @Test
    fun `getAllSectionPositionsFor() returns valid positions for section 3x1`() {
        val positions = setOf(
            Position(6, 0), Position(6, 1), Position(6, 2),
            Position(7, 0), Position(7, 1), Position(7, 2),
            Position(8, 0), Position(8, 1), Position(8, 2)
        )

        positions.forEach { position ->
            assertThat(Board().getAllSectionPositionsFor(position).toSet()).isEqualTo(positions)
        }
    }

    @Test
    fun `getAllSectionPositionsFor() returns valid positions for section 3x2`() {
        val positions = setOf(
            Position(6, 3), Position(6, 4), Position(6, 5),
            Position(7, 3), Position(7, 4), Position(7, 5),
            Position(8, 3), Position(8, 4), Position(8, 5)
        )

        positions.forEach { position ->
            assertThat(Board().getAllSectionPositionsFor(position).toSet()).isEqualTo(positions)
        }
    }

    @Test
    fun `getAllSectionPositionsFor() returns valid positions for section 3x3`() {
        val positions = setOf(
            Position(6, 6), Position(6, 7), Position(6, 8),
            Position(7, 6), Position(7, 7), Position(7, 8),
            Position(8, 6), Position(8, 7), Position(8, 8)
        )

        positions.forEach { position ->
            assertThat(Board().getAllSectionPositionsFor(position).toSet()).isEqualTo(positions)
        }
    }

    @Test
    fun `section() throws InvalidPosition in case of column index is equal to columnLength`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { Board().section(Position(0, Board.COLUMN_LENGTH)) }
    }

    @Test
    fun `section() throws InvalidPosition in case of column index is greater than to columnLength`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { Board().section(Position(0, Board.COLUMN_LENGTH + 1)) }
    }

    @Test
    fun `section() returns valid values`() {
        val board = Board()
        (0..2).forEach { rowIndex ->
            (0..2).forEach { columnIndex ->
                board[Position(rowIndex, columnIndex)] = rowIndex * 3 + columnIndex + 1
            }
        }
        assertThat(board.section(Position(0, 0)).toSet()).isEqualTo((1..9).toSet())
    }

    @Test
    fun `sectionCenters returns correct list of positions`() {
        assertThat(Board.SECTION_CENTERS.toSet()).isEqualTo(setOf(
            Position(1, 1), Position(1, 4), Position(1, 7),
            Position(4, 1), Position(4, 4), Position(4, 7),
            Position(7, 1), Position(7, 4), Position(7, 7)
        ))
    }

    @Test
    fun `isRowValid() returns true for valid rows`() {
        assertThat(validBoard.isRowValid(0)).isTrue
    }

    @Test
    fun `isRowValid() returns false for rows containing empty values`() {
        validBoard.unset(Position(0, 3))
        assertThat(validBoard.isRowValid(0)).isFalse
    }

    @Test
    fun `isRowValid() returns false for rows containing duplicate values`() {
        validBoard[Position(0, 2)] = validBoard[Position(0, 5)]
        assertThat(validBoard.isRowValid(0)).isFalse
    }

    @Test
    fun `isColumnValid() returns true for valid columns`() {
        assertThat(validBoard.isColumnValid(4)).isTrue
    }

    @Test
    fun `isColumnValid() returns false for columns containing empty values`() {
        validBoard.unset(Position(3, 4))
        assertThat(validBoard.isColumnValid(4)).isFalse
    }

    @Test
    fun `isColumnValid() returns false for columns containing duplicate values`() {
        validBoard[Position(3, 4)] = validBoard[Position(7, 4)]
        assertThat(validBoard.isColumnValid(4)).isFalse
    }

    @Test
    fun `isSectionValid() returns true for valid sections`() {
        assertThat(validBoard.isSectionValid(Board.SECTION_CENTERS.random())).isTrue
    }

    @Test
    fun `isSectionValid() returns false for sections containing empty values`() {
        val sectionCenter = Board.SECTION_CENTERS.random()
        validBoard.unset(sectionCenter)
        assertThat(validBoard.isSectionValid(sectionCenter)).isFalse
    }

    @Test
    fun `isSectionValid() returns false for sections containing duplicate values`() {
        val sectionCenter = Board.SECTION_CENTERS.random()
        validBoard[sectionCenter.previousRow] = validBoard[sectionCenter.nextRow]
        assertThat(validBoard.isSectionValid(sectionCenter)).isFalse
    }

    @Test
    fun `isValid returns true for valid board`() {
        assertThat(validBoard.isValid).isTrue
    }

    @Test
    fun `isValid returns false for board including empty values`() {
        validBoard.unset(Position(4, 5))
        assertThat(validBoard.isValid).isFalse
    }

    @Test
    fun `isValid returns false for invalid boards`() {
        validBoard[Position(3, 7)] = validBoard[Position(1, 1)]
        assertThat(validBoard.isValid).isFalse
    }

    @Test
    fun `isComplete returns true in case no empty values are in the Board`() {
        assertThat(validBoard.isComplete).isTrue
    }

    @Test
    fun `isComplete returns false in case empty values are in the Board`() {
        validBoard.unset(Position(5, 6))
        assertThat(validBoard.isComplete).isFalse
    }

    @Test
    fun `copy constructor creates correct copy`() {
        val copiedBoard = Board(validBoard)
        for(rowIndex in 0 until Board.ROW_LENGTH) {
            for (columnIndex in 0 until Board.COLUMN_LENGTH) {
                val position = Position(rowIndex, columnIndex)
                assertThat(copiedBoard[position]).isEqualTo(validBoard[position])
            }
        }
    }

    @Test
    fun `SECTION_POSITIONS contains all centers`() {
        for(center in Board.SECTION_CENTERS) assertThat(Board.SECTION_POSITIONS[center]?.size).isEqualTo(9)
    }

    @Test
    fun `SECTION_POSITIONS is correct for all centers`() {
        for(center in Board.SECTION_CENTERS) {
            val sectionPositions = Board.SECTION_POSITIONS[center] ?: emptyList()
            for (rowIndex in (center.row - 1)..(center.row + 1))
                for (columnIndex in (center.column - 1)..(center.column + 1))
                    assertThat(sectionPositions).contains(Position(rowIndex, columnIndex))
        }
    }

    @Test
    fun `Position's next() works correctly within boundaries at the beginning of a row`() {
        val position = Position(2, 0)
        val next = position.next()
        assertThat(next.row).isEqualTo((position.row))
        assertThat(next.column).isEqualTo(position.column + 1)
    }

    @Test
    fun `Position's next() works correctly within boundaries in the middle of a row`() {
        val position = Position(2, 4)
        val next = position.next()
        assertThat(next.row).isEqualTo((position.row))
        assertThat(next.column).isEqualTo(position.column + 1)
    }

    @Test
    fun `Position's next() works correctly within boundaries at the end of a row`() {
        val position = Position(2, Board.COLUMN_LENGTH - 1)
        val next = position.next()
        assertThat(next.row).isEqualTo((position.row + 1))
        assertThat(next.column).isEqualTo(0)
    }

    @Test
    fun `Position's next() throws InvalidPosition if initial position is out of bounds`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { Position(3, Board.COLUMN_LENGTH).next() }
    }

    @Test
    fun `Position's next() throws InvalidPosition if next position is out of bounds`() {
        assertThatExceptionOfType(InvalidPosition::class.java).isThrownBy { Position(Board.ROW_LENGTH - 1, Board.COLUMN_LENGTH - 1).next() }
    }
}