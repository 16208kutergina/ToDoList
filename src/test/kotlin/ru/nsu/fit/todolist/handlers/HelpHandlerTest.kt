package ru.nsu.fit.todolist.handlers

import io.mockk.mockk
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.TaskFileManager

internal class HelpHandlerTest : ConsoleOutputTest() {
    private val helpHandler = HelpHandler()
    private val taskFileManager = mockk<TaskFileManager>()

    @Test
    fun handleHelpTest() {
        val command = Command("help", "")
        val executionResult = helpHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.SUCCESS, executionResult)
        assertEquals(helpText, outputStream.toString().trimIndent())
    }

    @Test
    fun handleUnknownCommandTest() {
        val command = Command("abracadabra", "")
        val executionResult = helpHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.UNKNOWN_COMMAND, executionResult)
        assertEquals("${ExecutionResult.UNKNOWN_COMMAND.text}\n$helpText", outputStream.toString().trimIndent())
    }

}