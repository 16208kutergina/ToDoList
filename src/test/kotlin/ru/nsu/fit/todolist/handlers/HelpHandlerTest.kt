package ru.nsu.fit.todolist.handlers

import io.mockk.mockk
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.TaskFileManager

internal class HelpHandlerTest : ConsoleOutputTest() {
    private val helpHandler = HelpHandler()
    private val taskFileManager = mockk<TaskFileManager>()

    private val helpText = """
    help - print instruction            
    add <name task> - add new task
    done <number_task1 number_task2 ...> - mark tasks DONE
    delete <number_task1 number_task2 ...> - delete tasks
    list        - print all tasks
         -done  - print DONE tasks
         -todo  - print TODO tasks
                next - next part(10) tasks
                stop - return to main console
    exit - exit from application
            """.trimIndent()

    @Test
    fun handleHelpTest() {
        val command = Command("help", "")
        val executionResult = helpHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.SUCCESS, executionResult)
        assertEquals(helpText, outputStream.toString().trimIndent())
    }

    @Test
    fun handleUnknownCommandTest() {
        val command = Command("abracadabra", "")
        val executionResult = helpHandler.handle(command, taskFileManager)
        assertEquals(ExecutionResult.UNKNOWN_COMMAND, executionResult)
        assertEquals(helpText, outputStream.toString().trimIndent())
    }

}