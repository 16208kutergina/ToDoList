package ru.nsu.fit.todolist

import ru.nsu.fit.todolist.handlers.*
import java.util.*

class HandlerFactory() {
    private val handlers = HashMap<String, Handler>()
    private val helpHandler = HelpHandler()

    fun getHandler(command: Command): Handler {
        return handlers.getOrDefault(command.nameCommand, helpHandler)
    }

    init {
        handlers["add"] = AddHandler()
        handlers["delete"] = DeleteHandler()
        handlers["done"] = DoneHandler()
        handlers["list"] = ListHandler()
        handlers["exit"] = ExitHandler()
        handlers["help"] = helpHandler
    }
}