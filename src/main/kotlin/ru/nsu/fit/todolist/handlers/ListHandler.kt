package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.*

class ListHandler(private val consoleReaderListTask: ConsoleReaderUserAnswer = ConsoleReaderUserAnswer()) : Handler {
    private val countReadableTasks = 10

    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        val filterMode = determineFilterMode(command)
            ?: return ExecutionResult.UNKNOWN_MODE_SORT
        return taskFileManager.read(ExecutionResult.FILE_PROBLEM,
            { userDialog(taskFileManager, filterMode) })
    }

    private fun userDialog(taskFileManager: TaskFileManager, filterMode: FilterComposed): ExecutionResult {
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

    private fun determineFilterMode(command: Command): FilterComposed? {
        val filterComposed = FilterComposed()
        command.arguments
            .split(" ")
            .forEach {
                it.trim()
                val filter = when (it) {
                    "-todo" -> FilterStatus(StatusTask.TODO)
                    "-done" -> FilterStatus(StatusTask.DONE)
                    "" -> FilterAll()
                    else -> return null
                }
                filterComposed.add(filter)
            }
        return filterComposed
    }


    private fun getFilteredList(
        sequence: Sequence<Task>,
        filterMode: FilterComposed
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
}



