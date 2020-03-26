package ru.nsu.fit.todolist.handlers


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.CommandExceptions
import ru.nsu.fit.todolist.TaskFileManager

internal class AddHandlerTest {
    private val taskFileManager = TaskFileManager("testFile.json")
    private val addHandler = AddHandler()

    @Test
    fun handleUnnamedTaskTest() {
        val command = Command("add", "")
        val commandExceptions = addHandler.handle(command, taskFileManager)
        assertEquals(CommandExceptions.UNNAMED_TASK, commandExceptions)
    }

    @Test
    fun handleFileProblemTest() {
        val taskFileManager = TaskFileManager("")
        val command = Command("add", "task")
        val commandExceptions = addHandler.handle(command, taskFileManager)
        assertEquals(CommandExceptions.FILE_PROBLEM, commandExceptions)
    }

    @Test
    fun handleSuccessTest() {
        val command = Command("add", "task")
        val commandExceptions = addHandler.handle(command, taskFileManager)
        assertEquals(CommandExceptions.SUCCESS, commandExceptions)
    }
}