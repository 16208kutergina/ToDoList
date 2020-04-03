package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.Command
import ru.nsu.fit.todolist.ExecutionResult
import ru.nsu.fit.todolist.Handler
import ru.nsu.fit.todolist.TaskFileManager

class HelpHandler : Handler {
    private val helpText = """
    help - print instruction            
    add <name task> - add new task
    done <number_task1 number_task2 ...> - mark tasks DONE
    delete <number_task1 number_task2 ...> - delete tasks
    list        - print all tasks
         -done  - print DONE tasks
         -todo  - print TODO tasks
                next - next part(10) tasks
                stop - return to main console
    exit - exit from application
            """.trimIndent()


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