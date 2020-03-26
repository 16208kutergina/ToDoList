package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.*
import java.io.IOException

class AddHandler : Handler {
    override fun handle(command: Command, taskFileManager: TaskFileManager): CommandExceptions {
        if(command.arguments.isEmpty()){
            return CommandExceptions.UNNAMED_TASK
        }
        val task = Task(StatusTask.TODO, command.arguments)
        try {
            taskFileManager.write(task)
        }catch (e: IOException){
            return CommandExceptions.FILE_PROBLEM
        }
        return CommandExceptions.SUCCESS
    }
}
