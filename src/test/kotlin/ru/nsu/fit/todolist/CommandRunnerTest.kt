package ru.nsu.fit.todolist

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import ru.nsu.fit.todolist.handlers.AddHandler
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CommandRunnerTest {

    @Test
    fun runUnnamedTaskTest() {
        val command = Command("add", "")
        val handler = mockk<Handler>()
        every { handler.handle(command, any()) } returns ExecutionResult.UNNAMED_TASK
        val commandRunner = CommandRunner("")
        val commandExceptions = commandRunner.run(command)
        assertEquals(ExecutionResult.UNNAMED_TASK, commandExceptions)
    }

    @Test
    fun runFileProblemTest() {
        val command = Command("add", "task")
        val handler = mockk<Handler>()
        every { handler.handle(command, any()) } returns ExecutionResult.FILE_PROBLEM
        val commandRunner = CommandRunner("")
        val commandExceptions = commandRunner.run(command)
        assertEquals(ExecutionResult.FILE_PROBLEM, commandExceptions)
    }

    @Test
    fun runSuccessTest() {
        val command = Command("add", "task")
        val handler = mockk<Handler>()
        every { handler.handle(command, any()) } returns ExecutionResult.SUCCESS
        val commandRunner = CommandRunner("")
        val commandExceptions = commandRunner.run(command)
        assertEquals(ExecutionResult.SUCCESS, commandExceptions)
    }


}