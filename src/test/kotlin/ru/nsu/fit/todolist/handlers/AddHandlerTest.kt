package ru.nsu.fit.todolist.handlers


import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.TaskFileManager
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AddHandlerTest {
    private val fileName = "testFile.json"
    private val taskFileManager = TaskFileManager(fileName)
    private val addHandler = AddHandler()

    @Test
    fun handleUnnamedTaskTest() {
        val command = Command("add", "")
        val commandExceptions = addHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.UNNAMED_TASK, commandExceptions)
    }

    @Test
    fun handleFileProblemTest() {
        val taskFileManager = TaskFileManager("")
        val command = Command("add", "task")
        val commandExceptions = addHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.FILE_PROBLEM, commandExceptions)
    }

    @Test
    fun handleSuccessTest() {
        val command = Command("add", "task")
        val commandExceptions = addHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.SUCCESS, commandExceptions)
    }

    @AfterAll
    fun deleteFile(){
        val file = File(fileName)
        file.delete()
    }
}