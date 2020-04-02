package ru.nsu.fit.todolist.handlers

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ru.nsu.fit.todolist.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DeleteHandlerTest {
    private val deleteHandler = DeleteHandler()

    @Test
    fun handleWrongArguments() {
        val taskFileManager = mockk<TaskFileManager>()
        val command = Command("delete", "wrong argument")
        val executionResult = deleteHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.WRONG_FORMAT_ARGUMENTS, executionResult)
    }

    @Test
    fun handleCorrectArguments() {
        val taskFileManager = mockk<TaskFileManager>()
        every { taskFileManager.delete(any()) } just Runs
        val command = Command("delete", "2 3")
        val executionResult = deleteHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.SUCCESS, executionResult)
    }

    @Test
    fun handleEmptyArguments() {
        val taskFileManager = mockk<TaskFileManager>()
        val command = Command("delete", "")
        val executionResult = deleteHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.WRONG_FORMAT_ARGUMENTS, executionResult)
    }
}
