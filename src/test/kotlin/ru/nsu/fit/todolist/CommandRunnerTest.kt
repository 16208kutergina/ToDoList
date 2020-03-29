package ru.nsu.fit.todolist

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CommandRunnerTest {
    private val fileName = "testFile.json"
    private val commandRunner = CommandRunner(fileName)


    @Test
    fun runUnnamedTaskTest() {
        val command = Command("add", "")
        val commandExceptions = commandRunner.run(command)
        assertEquals(ExecutionResult.UNNAMED_TASK, commandExceptions)
    }

    @Test
    fun runFileProblemTest() {
        val commandRunner = CommandRunner("")
        val command = Command("add", "task")
        val commandExceptions = commandRunner.run(command)
        assertEquals(ExecutionResult.FILE_PROBLEM, commandExceptions)
    }

    @Test
    fun runSuccessTest() {
        val command = Command("add", "task")
        val commandExceptions = commandRunner.run(command)
        assertEquals(ExecutionResult.SUCCESS, commandExceptions)
    }

    @AfterAll
    fun deleteFile() {
        val file = File(fileName)
        file.delete()
    }
}