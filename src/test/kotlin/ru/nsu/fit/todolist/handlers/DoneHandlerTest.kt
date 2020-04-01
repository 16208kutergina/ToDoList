package ru.nsu.fit.todolist.handlers

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.TaskFileManager
import java.io.IOException

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
        every { taskFileManager.markDone(any()) } throws IOException()
        val executionResult = doneHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.FILE_PROBLEM, executionResult)
    }

    @Test
    fun handleSuccessTest() {
        val command = Command("done", "1 2")
        every { taskFileManager.markDone(any()) } just Runs
        val executionResult = doneHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.SUCCESS, executionResult)
    }
}