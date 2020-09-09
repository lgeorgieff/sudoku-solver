package com.gailswintha.sudoku.core.io

import com.gailswintha.sudoku.core.model.Board
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
                        6 7 8 9 -1 2 3 4 5
                        3 4 5 6 7 8 9 1 2
                        2 3 4 5 6 7 8 9 1
                        5 6 7 8 9 1 2 -1 4
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
}