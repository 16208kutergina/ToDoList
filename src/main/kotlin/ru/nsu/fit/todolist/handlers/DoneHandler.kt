package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.Handler
import ru.nsu.fit.todolist.TaskFileManager

class DoneHandler : Handler {
    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        TODO("Not yet implemented")
    }

}
