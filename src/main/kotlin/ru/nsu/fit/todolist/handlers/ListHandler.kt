package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.*
import java.util.*

class ListHandler : Handler {
    private val countReadableTasks = 10
    private var inputStream = System.`in`

    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        taskFileManager.openForRead()
        val executionResult = userDialog(command, taskFileManager)
        taskFileManager.closeForRead()
        return executionResult
    }

    private fun userDialog(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        val filterMode = determineFilterMode(command)
        if(filterMode == FilterMode.UNDEFINED){
            return ExecutionResult.UNKNOWN_MODE_SORT
        }
        val scanner = Scanner(inputStream)
        val consoleReaderListTask = ConsoleReaderUserAnswer()

        var listTask = getFilteredList(taskFileManager, filterMode)
        while (listTask != null) {
            printListTask(listTask)
            listTask = getFilteredList(taskFileManager, filterMode)
            listTask ?: break
            val readUserAnswer = consoleReaderListTask.askUserForContinue(scanner)
            if (readUserAnswer == UserAction.STOP) {
                break
            }
        }
        return ExecutionResult.SUCCESS
    }

    private fun determineFilterMode(command: Command): FilterMode {
        return when (command.arguments) {
            "-todo" -> FilterMode.TODO
            "-done" -> FilterMode.DONE
            "" -> FilterMode.ALL
            else -> FilterMode.UNDEFINED
        }
    }


    private fun getFilteredList(
        taskFileManager: TaskFileManager,
        filterMode: FilterMode
    ): List<Pair<Int, Task>>? {
        return taskFileManager
            .getTaskSeq()
            .mapIndexed { index, it -> Pair(index + 1, it) }
            .filter { filterMode.isAccept(it.second) }
            .chunked(countReadableTasks)
            .firstOrNull()
    }

    private fun printListTask(lastReadableTask: List<Pair<Int, Task>>) {
        for (it in lastReadableTask) {
            println("${it.first}. ${it.second}")
        }
    }


    enum class FilterMode {
        ALL {
            override fun isAccept(task: Task): Boolean
                    = true
        },
        TODO {
            override fun isAccept(task: Task): Boolean
                    = task.status == StatusTask.TODO
            }
        ,
        DONE {
            override fun isAccept(task: Task): Boolean
            = task.status == StatusTask.DONE
        },
        UNDEFINED {
            override fun isAccept(task: Task): Boolean = false
        }
        ;

        abstract fun isAccept(task: Task): Boolean
    }
}



