package ru.nsu.fit.todolist.handlers

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ru.nsu.fit.todolist.*

import java.io.File
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DeleteHandlerTest {
    private val fileName = "testFile.json"
    private val taskFileManager = TaskFileManager(fileName)
    private val deleteHandler = DeleteHandler()

    @Test
    fun handleWrongArguments() {
        val command = Command("delete", "wrong argument")
        val executionResult = deleteHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.WRONG_FORMAT_ARGUMENTS, executionResult)
    }

    @Test
    fun handleCorrectArgumentsButNoExist() {
        val command = Command("delete", "2 3")
        val executionResult = deleteHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.SUCCESS, executionResult)
    }

    @Test
    fun handleEmptyArguments() {
        val command = Command("delete", "")
        val executionResult = deleteHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.WRONG_FORMAT_ARGUMENTS, executionResult)
    }

    @Test
    fun handleCorrectArguments() {
        val addHandler = AddHandler()
        val startListTask = LinkedList<Task>()
        for (n in 0 until 10) {
            val name = "task$n"
            val command = Command("add", name)
            startListTask.add(Task(StatusTask.TODO, name))
            addHandler.handle(command, taskFileManager)
        }

        val command = Command("delete", "2 3")

        val executionResult = deleteHandler.handle(command, taskFileManager)

        val actual = LinkedList<Task>()
        taskFileManager.openForRead()
        for (it in taskFileManager.getTaskSeq()) {
            actual.add(it)
        }
        taskFileManager.closeForRead()

        assertEquals(ExecutionResult.SUCCESS, executionResult)
        val expect = startListTask.also {
            it.removeAt(2)
            it.removeAt(1)
        }
        assertEquals(expect, actual)
    }


    @AfterAll
    fun deleteFile() {
        val file = File(fileName)
        file.delete()
    }
}
