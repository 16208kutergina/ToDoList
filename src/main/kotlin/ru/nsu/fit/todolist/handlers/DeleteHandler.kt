package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.Handler
import ru.nsu.fit.todolist.TaskFileManager
import java.io.IOException

class DeleteHandler : Handler {
    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        return try {
            val arguments = command.arguments
                .split(" ")
                .map { it.toInt() }
            if (arguments.isNotEmpty()) {
                taskFileManager.delete(
                    arguments
                )
            } else {
                ExecutionResult.SUCCESS
            }
        } catch (e: NumberFormatException) {
            ExecutionResult.WRONG_FORMAT_ARGUMENTS
        }
    }

}
