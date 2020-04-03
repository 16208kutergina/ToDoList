package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.Handler
import ru.nsu.fit.todolist.TaskFileManager

class HelpHandler : Handler {

    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        return if (command.nameCommand != "help") {
            println("${ExecutionResult.UNKNOWN_COMMAND.text}\n$helpText")
            ExecutionResult.UNKNOWN_COMMAND
        } else {
            println(helpText)
            return ExecutionResult.SUCCESS
        }
    }
}
