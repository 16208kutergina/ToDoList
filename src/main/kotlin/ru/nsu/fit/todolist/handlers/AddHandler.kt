package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.*
import java.io.IOException

class AddHandler : Handler {
    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        if(command.arguments.isEmpty()){
            return ExecutionResult.UNNAMED_TASK
        }
        val task = Task(StatusTask.TODO, command.arguments)
        try {
            taskFileManager.write(task)
        }catch (e: IOException){
            return ExecutionResult.FILE_PROBLEM
        }
        return ExecutionResult.SUCCESS
    }
}
