package ru.nsu.fit.todolist

import ru.nsu.fit.todolist.handlers.HelpHandler
import java.io.InputStream
import java.util.*

class ProgramStarter(inputStream: InputStream, fileName: String = "todo-list.json") {
    private val taskFileManager = TaskFileManager(fileName)
    private val commandRunner = HandlerFactory()
    private val scanner = Scanner(inputStream)

    fun start() {
        HelpHandler().handle(Command("help", ""), taskFileManager)
        while (true) {
            print("write command:>")
            val line = scanner.nextLine()
            val command = parseCommand(line) ?: continue
            val handler = commandRunner.getHandler(command)

            val executionResult = handler.handle(command, taskFileManager)

            if (executionResult == ExecutionResult.EXIT) {
                break
            }
            if (executionResult != ExecutionResult.SUCCESS) {
                println(executionResult.text)
            }
        }
        scanner.close()
    }

    private fun parseCommand(line: String): Command? {
        val instructions = line.trim()
            .split(" ".toRegex(), 2)
            .map { it.trim() }
        return when {
            instructions.first().isEmpty() -> null
            instructions.size == 1 -> Command(
                instructions.first(),
                ""
            )
            else -> Command(
                instructions.first(),
                instructions.last()
            )
        }
    }

}