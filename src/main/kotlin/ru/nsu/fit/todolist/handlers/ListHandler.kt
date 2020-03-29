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
        val sortMode = determineSortMode(command)
        val scanner = Scanner(inputStream)
        val consoleReaderListTask = ConsoleReaderUserAnswer()

        while (true) {
            val readyListTask = getReadyListTask(taskFileManager, sortMode)
            printListTask(readyListTask)
            if (!taskFileManager.hasNextRead()) {
                break
            }
            val readUserAnswer = consoleReaderListTask.askUserForContinue(scanner)
            if (readUserAnswer == UserAction.STOP) {
                break
            }

        }
    }

    private fun determineSortMode(command: Command): Enum<*> {
        val sortMode = when (command.arguments) {
            "-todo" -> SortMode.TODO
            "-done" -> SortMode.DONE
            "" -> SortMode.ALL
            else -> ExecutionResult.UNKNOWN_MODE_SORT
        }
        return sortMode
    }


    private fun getReadyListTask(taskFileManager: TaskFileManager, sortMode: Enum<*>): List<Task?> {
        val lastReadableTask = LinkedList<Task?>()
        while (lastReadableTask.size != countReadableTasks) {
            val count = countReadableTasks - lastReadableTask.size
            val readNext = taskFileManager.readNextListTask(count)
            if (readNext.isEmpty()) {
                break
            }
            val sortReadableTask = sortReadableTask(sortMode, readNext)
            lastReadableTask.addAll(sortReadableTask)
        }
        return lastReadableTask
    }

    private fun sortReadableTask(sortMode: Enum<*>, readNext: List<Task>): List<Task> {
        return when (sortMode) {
            SortMode.ALL -> readNext
            SortMode.TODO -> readNext.filter { it.status == StatusTask.TODO }
            SortMode.DONE -> readNext.filter { it.status == StatusTask.DONE }
            else -> emptyList()
        }
    }

    private fun printListTask(lastReadableTask: List<Task?>) {
        for (it in lastReadableTask) {
            indexTask++
            println("$indexTask. ${it.toString()}")
        }
    }


    enum class SortMode {
        ALL,
        TODO,
        DONE
    }
}



