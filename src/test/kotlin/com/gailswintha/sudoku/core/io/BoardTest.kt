package com.gailswintha.sudoku.core.io

import com.gailswintha.sudoku.core.model.Board
import com.gailswintha.sudoku.core.model.Board.Companion.X
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import java.io.File
import java.io.Reader

class BoardTest {
    private fun withResource(resourcePath: String, fn: (Reader) -> Unit) {
        this::class.java.getResource(resourcePath).openStream().use { jsonStream ->
            jsonStream.bufferedReader().use { jsonReader -> fn(jsonReader) }
        }
    }

    @Test
    fun `loadFromJsonFile() loads json file successfully`() {
        withResource("/valid_board.json") { jsonReader ->
            assertThat(Board.loadFromJsonFile(jsonReader).toString()).isEqualTo(
                """
                        1 2 3 4 5 6 7 8 9
                        7 8 9 1 2 3 4 5 6
                        4 5 6 7 8 9 1 2 3
                        9 1 2 3 4 5 6 7 8
                        6 7 8 9 1 2 3 4 5
                        3 4 5 6 7 8 9 1 2
                        2 3 4 5 6 7 8 9 1
                        5 6 7 8 9 1 2 3 4
                        8 9 1 2 3 4 5 6 7
                    """.trimIndent()
            )
        }
    }

    @Test
    fun `loadFromJsonFile() loads json file including empty fields successfully`() {
        withResource("/valid_baord_including_empty_fields.json") { jsonReader ->
            assertThat(Board.loadFromJsonFile(jsonReader).toString()).isEqualTo(
                """
                        1 2 3 4 5 6 7 8 9
                        7 8 9 1 2 3 4 5 6
                        4 5 6 7 8 9 1 2 3
                        9 1 2 3 4 5 6 7 8
                        6 7 8 9 _ 2 3 4 5
                        3 4 5 6 7 8 9 1 2
                        2 3 4 5 6 7 8 9 1
                        5 6 7 8 9 1 2 _ 4
                        8 9 1 2 3 4 5 6 7
                    """.trimIndent()
            )
        }
    }

    @Test
    fun `loadFromJsonFile() throws IOException for bad file path`() {
        assertThatExceptionOfType(IOException::class.java).isThrownBy { Board.loadFromJsonFile(File("/does_not_exist.json")) }
    }

    @Test
    fun `loadFromJsonFile() throws IOException for invalid JSON structure in file`() {
        withResource("/invalid_json.json") { jsonReader ->
            assertThatExceptionOfType(IOException::class.java).isThrownBy { Board.loadFromJsonFile(jsonReader) }
        }
    }

    @Test
    fun `loadFromJsonFile() throws IllegalJson for wrong outer JSON in file`() {
        withResource("/wrong_outer_json.json") { jsonReader ->
            assertThatExceptionOfType(IllegalJson::class.java).isThrownBy { Board.loadFromJsonFile(jsonReader) }
        }
    }

    @Test
    fun `loadFromJsonFile() throws IllegalJson for wrong inner JSON in file`() {
        withResource("/wrong_inner_json.json") { jsonReader ->
            assertThatExceptionOfType(IllegalJson::class.java).isThrownBy { Board.loadFromJsonFile(jsonReader) }
        }
    }

    @Test
    fun `loadFromJsonFile() throws IllegalJson for invalid value in JSON file`() {
        withResource("/invalid_value.json") { jsonReader ->
            assertThatExceptionOfType(IllegalJson::class.java).isThrownBy { Board.loadFromJsonFile(jsonReader) }
        }
    }

    @Test
    fun `loadFromArray() loads data successfully`() {
        val data = arrayOf(
            arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            arrayOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
            arrayOf(4, 5, 6, 7, 8, 9, 1, 2, 3),
            arrayOf(9, 1, 2, 3, 4, 5, 6, 7, 8),
            arrayOf(6, 7, 8, 9, 1, 2, 3, 4, 5),
            arrayOf(3, 4, 5, 6, 7, 8, 9, 1, 2),
            arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 1),
            arrayOf(5, 6, 7, 8, 9, 1, 2, 3, 4),
            arrayOf(8, 9, 1, 2, 3, 4, 5, 6, 7)
        )

        assertThat(Board.loadFromArray(data).toString()).isEqualTo(
            """
                        1 2 3 4 5 6 7 8 9
                        7 8 9 1 2 3 4 5 6
                        4 5 6 7 8 9 1 2 3
                        9 1 2 3 4 5 6 7 8
                        6 7 8 9 1 2 3 4 5
                        3 4 5 6 7 8 9 1 2
                        2 3 4 5 6 7 8 9 1
                        5 6 7 8 9 1 2 3 4
                        8 9 1 2 3 4 5 6 7
                    """.trimIndent()
        )
    }

    @Test
    fun `loadFromArray() loads data including empty fields successfully`() {
        val data = arrayOf(
            arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            arrayOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
            arrayOf(4, 5, 6, 7, 8, 9, 1, 2, 3),
            arrayOf(9, 1, 2, 3, 4, 5, 6, 7, 8),
            arrayOf(6, 7, 8, 9, X, 2, 3, 4, 5),
            arrayOf(3, 4, 5, 6, 7, 8, 9, 1, 2),
            arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 1),
            arrayOf(5, 6, 7, 8, 9, 1, 2, X, 4),
            arrayOf(8, 9, 1, 2, 3, 4, 5, 6, 7)
        )

        assertThat(Board.loadFromArray(data).toString()).isEqualTo(
            """
                        1 2 3 4 5 6 7 8 9
                        7 8 9 1 2 3 4 5 6
                        4 5 6 7 8 9 1 2 3
                        9 1 2 3 4 5 6 7 8
                        6 7 8 9 _ 2 3 4 5
                        3 4 5 6 7 8 9 1 2
                        2 3 4 5 6 7 8 9 1
                        5 6 7 8 9 1 2 _ 4
                        8 9 1 2 3 4 5 6 7
                    """.trimIndent()
        )
    }

    @Test
    fun `loadFromArray() throws IllegalArgumentException in case a row is missing`() {
        val data = arrayOf(
            arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            arrayOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
            arrayOf(4, 5, 6, 7, 8, 9, 1, 2, 3),
            arrayOf(9, 1, 2, 3, 4, 5, 6, 7, 8),
            arrayOf(6, 7, 8, 9, 1, 2, 3, 4, 5),
            arrayOf(3, 4, 5, 6, 7, 8, 9, 1, 2),
            arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 1),
            arrayOf(5, 6, 7, 8, 9, 1, 2, 3, 4)
        )

        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Board.loadFromArray(data) }
    }

    @Test
    fun `loadFromArray() throws IllegalArgumentException in case of too many rows`() {
        val data = arrayOf(
            arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            arrayOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
            arrayOf(4, 5, 6, 7, 8, 9, 1, 2, 3),
            arrayOf(9, 1, 2, 3, 4, 5, 6, 7, 8),
            arrayOf(6, 7, 8, 9, 1, 2, 3, 4, 5),
            arrayOf(3, 4, 5, 6, 7, 8, 9, 1, 2),
            arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 1),
            arrayOf(5, 6, 7, 8, 9, 1, 2, 3, 4),
            arrayOf(8, 9, 1, 2, 3, 4, 5, 6, 7),
            arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        )

        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Board.loadFromArray(data) }
    }

    @Test
    fun `loadFromArray() throws IllegalArgumentException in case a column is missing`() {
        val data = arrayOf(
            arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            arrayOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
            arrayOf(4, 5, 6, 7, 8, 9, 1, 2, 3),
            arrayOf(9, 1, 2, 3, 4, 5, 6, 7),
            arrayOf(6, 7, 8, 9, 1, 2, 3, 4, 5),
            arrayOf(3, 4, 5, 6, 7, 8, 9, 1, 2),
            arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 1),
            arrayOf(5, 6, 7, 8, 9, 1, 2, 3, 4),
            arrayOf(8, 9, 1, 2, 3, 4, 5, 6, 7)
        )

        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Board.loadFromArray(data) }
    }

    @Test
    fun `loadFromArray() throws IllegalArgumentException in case of too may columns`() {
        val data = arrayOf(
            arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            arrayOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
            arrayOf(4, 5, 6, 7, 8, 9, 1, 2, 3),
            arrayOf(9, 1, 2, 3, 4, 5, 6, 7, 8),
            arrayOf(6, 7, 8, 9, 1, 2, 3, 4, 5),
            arrayOf(3, 4, 5, 6, 7, 8, 9, 1, 2, 9),
            arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 1),
            arrayOf(5, 6, 7, 8, 9, 1, 2, 3, 4),
            arrayOf(8, 9, 1, 2, 3, 4, 5, 6, 7)
        )

        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Board.loadFromArray(data) }
    }

    @Test
    fun `loadFromArray() throws IllegalArgumentException in case of invalid values`() {
        val data = arrayOf(
            arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            arrayOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
            arrayOf(4, 5, 6, 7, 8, 9, 1, 2, 3),
            arrayOf(9, 1, 2, 3, 4, 5, 6, 7, 8),
            arrayOf(6, 7, 8, 9, 1, 2, 3, 4, 5),
            arrayOf(3, 4, 5, 6, 99, 8, 9, 1, 2),
            arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 1),
            arrayOf(5, 6, 7, 8, 9, 1, 2, 3, 4),
            arrayOf(8, 9, 1, 2, 3, 4, 5, 6, 7)
        )

        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { Board.loadFromArray(data) }
    }
}