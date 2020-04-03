package ru.nsu.fit.todolist.handlers

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.TaskFileManager

internal class DoneHandlerTest {
    private val doneHandler = DoneHandler()
    private val taskFileManager = mockk<TaskFileManager>()

    @Test
    fun handleWrongArgumentTest() {
        val command = Command("done", "wrong argument")
        val executionResult = doneHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.WRONG_FORMAT_ARGUMENTS, executionResult)
    }

    @Test
    fun handleFileProblemTest() {
        val command = Command("done", "1 2")
        every { taskFileManager.markDone(any()) } returns ExecutionResult.FILE_PROBLEM
        val executionResult = doneHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.FILE_PROBLEM, executionResult)
    }

    @Test
    fun handleSuccessTest() {
        val command = Command("done", "1 2")
        every { taskFileManager.markDone(any()) } returns  ExecutionResult.SUCCESS
        val executionResult = doneHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.SUCCESS, executionResult)
    }
}