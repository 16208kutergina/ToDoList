package ru.nsu.fit.todolist

import ru.nsu.fit.todolist.handlers.HelpHandler

class ProgramStarter(
    private val taskFileManager: TaskFileManager,
    private val handlerFactory: HandlerFactory
) {

    fun start() {
        HelpHandler().handle(Command("help", ""), taskFileManager)
        while (true) {
            print("write command:>")
            val line = readLine() ?: break //check close stream
            val command = parseCommand(line) ?: continue
            val handler = handlerFactory.getHandler(command)

            val executionResult = handler.handle(command, taskFileManager)

            if (executionResult == ExecutionResult.EXIT) {
                break
            }
            if (executionResult != ExecutionResult.SUCCESS
                && executionResult != ExecutionResult.UNKNOWN_COMMAND
            ) {
                println(executionResult.text)
            }
        }
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
