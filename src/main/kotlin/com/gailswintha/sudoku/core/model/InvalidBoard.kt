package com.gailswintha.sudoku.core.model

class InvalidBoard (board: Board, cause: Throwable? = null) : Exception("Invalid board detected.\n$board", cause)