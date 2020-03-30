package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.Handler
import ru.nsu.fit.todolist.TaskFileManager

class DoneHandler : Handler {
    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        try {
            val arguments = command.arguments
                .split(" ")
                .map { it.toInt() }
            if (arguments.isNotEmpty())
                taskFileManager.markDone(arguments)
        } catch (e: NumberFormatException) {
            return ExecutionResult.WRONG_FORMAT_ARGUMENTS
        }
        return ExecutionResult.SUCCESS
    }

}
