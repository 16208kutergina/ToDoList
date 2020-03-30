package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.*
import java.util.*

class ListHandler : Handler {
    private val countReadableTasks = 10

    private var inputStream = System.`in`
    private var indexTask = 0

    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        indexTask = 0
        taskFileManager.openForRead()
        userDialog(command, taskFileManager)
        taskFileManager.closeForRead()
        return ExecutionResult.SUCCESS
    }

    private fun userDialog(command: Command, taskFileManager: TaskFileManager) {
        val filterMode = determineFilterMode(command)
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
    }

    private fun getFilteredList(
        taskFileManager: TaskFileManager,
        filterMode: Enum<*>
    ): List<Task>? {
        return taskFileManager
            .getTaskSeq()
            .filter { isFilterTask(filterMode, it) }
            .chunked(countReadableTasks)
            .firstOrNull()
    }


    private fun determineFilterMode(command: Command): Enum<*> {
        return when (command.arguments) {
            "-todo" -> FilterMode.TODO
            "-done" -> FilterMode.DONE
            "" -> FilterMode.ALL
            else -> ExecutionResult.UNKNOWN_MODE_SORT
        }
    }

    private fun isFilterTask(filterMode: Enum<*>, task: Task): Boolean {
        return when (filterMode) {
            FilterMode.ALL -> true
            FilterMode.TODO -> task.status == StatusTask.TODO
            FilterMode.DONE -> task.status == StatusTask.DONE
            else -> false
        }
    }

    private fun printListTask(lastReadableTask: List<Task?>) {
        for (it in lastReadableTask) {
            indexTask++
            println("$indexTask. ${it.toString()}")
        }
    }


    enum class FilterMode {
        ALL,
        TODO,
        DONE
    }
}



