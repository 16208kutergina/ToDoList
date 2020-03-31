package ru.nsu.fit.todolist

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ProgramStarterTest {
    private lateinit var outContent: ByteArrayOutputStream
    private val originalOut = System.out

    @BeforeEach
    fun setNewByteArray(){
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun startAdd() {
        val inputStream = "add my task\nexit".byteInputStream()
        val programStarter = ProgramStarter(inputStream, "test-todo-list.json")
        programStarter.start()
        inputStream.close()
        val out = outContent.toString()
        val expect = "write command:>write command:>"
        assertEquals(expect, out)
    }

    @Test
    fun startEmpty() {
        val inputStream = "\nexit".byteInputStream()
        val programStarter = ProgramStarter(inputStream, "test-todo-list.json")
        programStarter.start()
        inputStream.close()
        val out = outContent.toString()
        val expect = "write command:>write command:>"
        assertEquals(expect, out)
    }

    @AfterAll
    fun restoreStreams(){
        System.setOut(originalOut)
    }
}