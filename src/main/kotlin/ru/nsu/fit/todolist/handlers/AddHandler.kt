package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.*

class AddHandler : Handler {
    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        if (command.arguments.isEmpty()) {
            return ExecutionResult.UNNAMED_TASK
        }
        val task = Task(StatusTask.TODO, command.arguments)
        return taskFileManager.write(task)
    }
}
