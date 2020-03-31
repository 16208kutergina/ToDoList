package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.Handler
import ru.nsu.fit.todolist.TaskFileManager
import java.io.IOException

class DeleteHandler : Handler {
    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        try {
            val arguments = command.arguments
                .split(" ")
                .map { it.toInt() }
            if (arguments.isNotEmpty())
                taskFileManager.delete(arguments)
        } catch (e: NumberFormatException) {
            return ExecutionResult.WRONG_FORMAT_ARGUMENTS
        }catch (e: IOException){
            return ExecutionResult.FILE_PROBLEM
        }
        return ExecutionResult.SUCCESS
    }

}
