package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.*
import java.io.IOException

class ListHandler(private val consoleReaderListTask: ConsoleReaderUserAnswer = ConsoleReaderUserAnswer()) : Handler {
    private val countReadableTasks = 10

    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        val filterMode = determineFilterMode(command)
        if (filterMode == FilterMode.UNDEFINED) {
            return ExecutionResult.UNKNOWN_MODE_SORT
        }
        var executionResult: ExecutionResult
        try {
            taskFileManager.openForRead()
            executionResult = userDialog(taskFileManager, filterMode)
            taskFileManager.closeForRead()
        } catch (e: IOException) {
            executionResult = ExecutionResult.FILE_PROBLEM
        }
        return executionResult
    }

    private fun userDialog(taskFileManager: TaskFileManager, filterMode: FilterMode): ExecutionResult {
        val seq = taskFileManager.getTaskSequence()

        val listTask = getFilteredList(seq, filterMode).iterator()
        while (listTask.hasNext()) {
            printListTask(listTask.next())
            if (!listTask.hasNext()) {
                break
            }
            val readUserAnswer = consoleReaderListTask.askUserForContinue()
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
        sequence: Sequence<Task>,
        filterMode: FilterMode
    ): Sequence<List<Pair<Int, Task>>> {
        return sequence
            .mapIndexed { index, it -> Pair(index + 1, it) }
            .filter { filterMode.isAccept(it.second) }
            .chunked(countReadableTasks)
    }

    private fun printListTask(lastReadableTasks: List<Pair<Int, Task>>) {
        for (it in lastReadableTasks) {
            println("${it.first}. ${it.second}")
        }
    }


    enum class FilterMode {
        ALL {
            override fun isAccept(task: Task): Boolean = true
        },
        TODO {
            override fun isAccept(task: Task): Boolean = task.status == StatusTask.TODO
        }
        ,
        DONE {
            override fun isAccept(task: Task): Boolean = task.status == StatusTask.DONE
        },
        UNDEFINED {
            override fun isAccept(task: Task): Boolean = false
        }
        ;

        abstract fun isAccept(task: Task): Boolean
    }
}



