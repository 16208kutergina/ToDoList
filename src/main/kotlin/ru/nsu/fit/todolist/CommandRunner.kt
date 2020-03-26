package ru.nsu.fit.todolist

import ru.nsu.fit.todolist.handlers.AddHandler
import ru.nsu.fit.todolist.handlers.DeleteHandler
import ru.nsu.fit.todolist.handlers.DoneHandler
import ru.nsu.fit.todolist.handlers.ListHandler
import java.util.*

class CommandRunner {
    private val handlers = HashMap<String, Handler>()

    fun run(command: Command): CommandExceptions {
        val taskFileManager = TaskFileManager()
        val handler = handlers[command.command] ?: return CommandExceptions.UNKNOWN_COMMAND
        return handler.handle(command, taskFileManager)
    }

    init {
        handlers["add"] = AddHandler()
        handlers["delete"] = DeleteHandler()
        handlers["done"] = DoneHandler()
        handlers["list"] = ListHandler()
    }
}