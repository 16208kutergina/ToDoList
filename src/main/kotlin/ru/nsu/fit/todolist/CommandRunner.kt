package ru.nsu.fit.todolist

import ru.nsu.fit.todolist.handlers.*
import java.util.*

class CommandRunner(fileName: String) {
    private val handlers = HashMap<String, Handler>()
    private val taskFileManager = TaskFileManager(fileName)

    fun run(command: Command): ExecutionResult {
        val handler = handlers[command.command] ?: return ExecutionResult.UNKNOWN_COMMAND
        return handler.handle(command, taskFileManager)
    }

    init {
        handlers["add"] = AddHandler()
        handlers["delete"] = DeleteHandler()
        handlers["done"] = DoneHandler()
        handlers["list"] = ListHandler()
        handlers["exit"] = ExitHandler()
    }
}