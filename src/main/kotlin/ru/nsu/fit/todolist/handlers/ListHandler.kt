package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.CommandExceptions
import ru.nsu.fit.todolist.Handler
import ru.nsu.fit.todolist.TaskFileManager

class ListHandler : Handler {
    override fun handle(command: Command, taskFileManager: TaskFileManager): CommandExceptions {
        TODO("Not yet implemented")
    }

}
