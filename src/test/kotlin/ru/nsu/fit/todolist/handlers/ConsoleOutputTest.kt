package ru.nsu.fit.todolist.handlers

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class ConsoleOutputTest  {
    protected lateinit var outputStream: ByteArrayOutputStream
    private val originalOut = System.out

    @BeforeEach
    fun setNewByteArray() {
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
    }

    @AfterEach
    fun closeStream() {
        outputStream.close()
    }

    @AfterAll
    fun restoreStreams() {
        System.setOut(originalOut)
    }
}