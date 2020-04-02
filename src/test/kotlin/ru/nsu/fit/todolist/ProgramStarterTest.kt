package ru.nsu.fit.todolist

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import ru.nsu.fit.todolist.handlers.ConsoleOutputTest

internal class ProgramStarterTest : ConsoleOutputTest() {
    private val taskFileManager = mockk<TaskFileManager>()
    private val handlerFactory = mockk<HandlerFactory>()
    private val programStarter = ProgramStarter(taskFileManager, handlerFactory)
    private val originalIn = System.`in`
    private val handler = mockk<Handler>()
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

    @BeforeAll
    fun setHandle() {
        every { handlerFactory.getHandler(any()) } returns handler
        every { handler.handle(Command("exit", ""), taskFileManager) } returns ExecutionResult.EXIT
    }

    @Test
    fun startEnterTest() {
        val inputStream =
            "\nexit\n".byteInputStream()
        System.setIn(inputStream)
        programStarter.start()
        val outputText = outputStream.toString()
        inputStream.close()
        assertEquals("${helpText}\r\nwrite command:>write command:>", outputText)
    }

    @Test
    fun startCloseStreamTest() {
        val inputStream = "".byteInputStream()
        System.setIn(inputStream)
        inputStream.close()
        programStarter.start()
        val outputText = outputStream.toString()
        assertEquals("${helpText}\r\nwrite command:>", outputText)
    }

    @Test
    fun startExitTest() {
        val inputStream =
            "exit\n".byteInputStream()
        System.setIn(inputStream)
        programStarter.start()
        val outputText = outputStream.toString()
        inputStream.close()
        assertEquals("${helpText}\r\nwrite command:>", outputText)
    }

    @Test
    fun startSuccessTest() {
        every { handler.handle(any(), taskFileManager) } returns ExecutionResult.SUCCESS
        val inputStream =
            "list\nexit\n".byteInputStream()
        System.setIn(inputStream)
        programStarter.start()
        val outputText = outputStream.toString()
        inputStream.close()
        assertEquals("${helpText}\r\nwrite command:>write command:>write command:>", outputText)
    }

    @Test
    fun startUnknownCommandTest() {
        every { handler.handle(any(), taskFileManager) } returns ExecutionResult.UNKNOWN_COMMAND
        val inputStream =
            "abracadabra\nexit\n".byteInputStream()
        System.setIn(inputStream)
        programStarter.start()
        val outputText = outputStream.toString()
        inputStream.close()
        assertEquals("${helpText}\r\nwrite command:>write command:>write command:>", outputText)

    }

    @AfterAll
    fun setOriginalIn() {
        System.setIn(originalIn)
    }


}