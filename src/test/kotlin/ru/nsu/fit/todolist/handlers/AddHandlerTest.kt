package ru.nsu.fit.todolist.handlers

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.nsu.fit.todolist.*
import java.io.IOException

internal class AddHandlerTest {
    private val addHandler = AddHandler()

    @Test
    fun handleUnnamedTaskTest() {
        val taskFileManager = mockk<TaskFileManager>()
        val command = Command("add", "")
        val commandExceptions = addHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.UNNAMED_TASK, commandExceptions)
    }

    @Test
    fun handleFileProblemTest() {
        val taskFileManager = mockk<TaskFileManager>()
        every { taskFileManager.write(any()) }.throws(IOException())
        val command = Command("add", "task")
        val executionResult = addHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.FILE_PROBLEM, executionResult)
    }

    @Test
    fun handleSuccessTest() {
        val taskFileManager = mockk<TaskFileManager>()
        every {
            taskFileManager.write(any())
        } just Runs
        val command = Command("add", "task")
        val commandExceptions = addHandler.handle(command, taskFileManager)

        assertEquals(ExecutionResult.SUCCESS, commandExceptions)
    }


}