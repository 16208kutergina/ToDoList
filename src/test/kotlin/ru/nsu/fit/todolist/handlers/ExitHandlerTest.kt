package ru.nsu.fit.todolist.handlers

import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.TaskFileManager

internal class ExitHandlerTest {
    private val exitHandler = ExitHandler()

    @Test
    fun handleTest() {
        val taskFileManager = mockk<TaskFileManager>()
        val command = mockk<Command>()
        val executionResult = exitHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.EXIT, executionResult)
    }
}