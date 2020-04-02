package ru.nsu.fit.todolist.handlers

import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.nsu.fit.todolist.*
import java.io.IOException

internal class ListHandlerTest : ConsoleOutputTest() {
    private val consoleReaderUserAnswer = mockk<ConsoleReaderUserAnswer>()
    private val listHandler = ListHandler(consoleReaderUserAnswer)
    private val taskFileManager = mockk<TaskFileManager>()
    private val originalList = listOf(
        Task(StatusTask.TODO, "task1"),
        Task(StatusTask.TODO, "task2"),
        Task(StatusTask.TODO, "task3"),
        Task(StatusTask.TODO, "task4"),
        Task(StatusTask.TODO, "task5"),
        Task(StatusTask.DONE, "task6"),
        Task(StatusTask.DONE, "task7"),
        Task(StatusTask.DONE, "task8"),
        Task(StatusTask.DONE, "task9"),
        Task(StatusTask.DONE, "task10"),
        Task(StatusTask.DONE, "task11"),
        Task(StatusTask.DONE, "task12"),
        Task(StatusTask.DONE, "task13")
    )

    @BeforeAll
    fun before(){
        every { taskFileManager.getTaskSequence() } returns originalList.asSequence()
        every { taskFileManager.openForRead() } just Runs
        every { taskFileManager.closeForRead() } just Runs
    }

    @Test
    fun handleUnknownFilter() {
        val command = Command("list", "-filter")
        val executionResult = listHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.UNKNOWN_MODE_SORT, executionResult)
    }

    @Test
    fun handleFileProblem() {
        val command = Command("list", "")
        every { taskFileManager.openForRead() } throws IOException()
        val executionResult = listHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.FILE_PROBLEM, executionResult)
    }

    @Test
    fun testList() {
        val command = Command("list", "")
        every { consoleReaderUserAnswer.askUserForContinue() } returns UserAction.NEXT

        val executionResult = listHandler.handle(command, taskFileManager)

        val expected = """
        1. TODO: task1
        2. TODO: task2
        3. TODO: task3
        4. TODO: task4
        5. TODO: task5
        6. DONE: task6
        7. DONE: task7
        8. DONE: task8
        9. DONE: task9
        10. DONE: task10
        11. DONE: task11
        12. DONE: task12
        13. DONE: task13
        """.trimIndent()
        assertEquals(expected, outputStream.toString().trimIndent())
        assertEquals(ExecutionResult.SUCCESS, executionResult)
    }

    @Test
    fun testListStop() {
        val command = Command("list", "")
        every { consoleReaderUserAnswer.askUserForContinue() } returns UserAction.STOP

        val executionResult = listHandler.handle(command, taskFileManager)

        val expected = """
        1. TODO: task1
        2. TODO: task2
        3. TODO: task3
        4. TODO: task4
        5. TODO: task5
        6. DONE: task6
        7. DONE: task7
        8. DONE: task8
        9. DONE: task9
        10. DONE: task10
        """.trimIndent()
        assertEquals(expected, outputStream.toString().trimIndent())
        assertEquals(ExecutionResult.SUCCESS, executionResult)
    }

    @Test
    fun testListDone() {
        val command = Command("list", "-done")
        every { consoleReaderUserAnswer.askUserForContinue() } returns UserAction.NEXT

        val executionResult = listHandler.handle(command, taskFileManager)

        val expected = """
        6. DONE: task6
        7. DONE: task7
        8. DONE: task8
        9. DONE: task9
        10. DONE: task10
        11. DONE: task11
        12. DONE: task12
        13. DONE: task13
        """.trimIndent()
        assertEquals(expected, outputStream.toString().trimIndent())
        assertEquals(ExecutionResult.SUCCESS, executionResult)
    }

    @Test
    fun testListTodo() {
        val command = Command("list", "-todo")
        every { consoleReaderUserAnswer.askUserForContinue() } returns UserAction.NEXT

        val executionResult = listHandler.handle(command, taskFileManager)

        val expected = """
        1. TODO: task1
        2. TODO: task2
        3. TODO: task3
        4. TODO: task4
        5. TODO: task5
        """.trimIndent()
        assertEquals(expected, outputStream.toString().trimIndent())
        assertEquals(ExecutionResult.SUCCESS, executionResult)
    }

}