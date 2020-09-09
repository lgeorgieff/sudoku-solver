package com.gailswintha.sudoku.core.io

import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import com.gailswintha.sudoku.core.model.*
import java.io.*

fun Board.Companion.loadFromJsonFile(jsonFile: File): Board {
    val jsonReader = try {
        FileReader(jsonFile)
    } catch (err: FileNotFoundException) {
        throw IOException("""Could not load "${jsonFile.absolutePath}". ${err.message}""", err)
    }

    return jsonReader.use { loadFromJsonFile(it) }
}

fun Board.Companion.loadFromJsonFile(jsonReader: Reader): Board {
    val board = Board()

    val json = try {
        Klaxon().parseJsonArray(jsonReader)
    } catch (err: IOException) {
        throw IOException("""Could not load JSON. ${err.message}""", err)
    } catch (err: Throwable) {
        throw IllegalJson("""JSON must structure must be an array of length $ROW_LENGTH. "${err.message}"""", err)
    }

    if(json.size != ROW_LENGTH) {
        throw IllegalJson("""JSON must structure must be an array of length $ROW_LENGTH, but is "$json"""")
    } else {
        json.forEachIndexed { rowIndex, jsonRow ->
            if(jsonRow !is JsonArray<*> || jsonRow.size != COLUMN_LENGTH) {
                throw IllegalJson("""Nested JSON must must be an array of length $COLUMN_LENGTH, but is "$jsonRow"""")
            }
            jsonRow.forEachIndexed { columnIndex, jsonValue ->
                if(jsonValue !is Int || (!isValidValue(jsonValue) && jsonValue != NO_VALUE)) {
                    throw IllegalJson("""JSON values must be ints in the range of $VALUE_RANGE, $NO_VALUE""")
                }
                if(jsonValue != -1) board[Position(rowIndex, columnIndex)] = jsonValue
            }
        }
    }

    return board
}