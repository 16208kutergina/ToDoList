package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.*

class ListHandler(private val consoleReaderListTask: ConsoleReaderUserAnswer = ConsoleReaderUserAnswer()) : Handler {
    private var countReadableTasks = 10
    private var isAllListTask = false

    override fun handle(command: Command, taskFileManager: TaskFileManager): ExecutionResult {
        val arguments = command.arguments.split(" ").toMutableList()
        setCountItemAtList(arguments)
        checkAllListTaskFilter(arguments)
        val filterMode = determineFilterMode(arguments)
            ?: return ExecutionResult.UNKNOWN_MODE_SORT
        return taskFileManager.getTasks { sequence -> userDialog(filterMode, sequence) }
    }

    private fun setCountItemAtList(arguments: MutableList<String>) {
        val find = arguments.find { it.contains("count") }
        arguments.remove(find)
        val count = find?.split("=")?.last()?.toInt()
        if (count != null) {
            countReadableTasks = count
        }
    }

    private fun checkAllListTaskFilter(arguments: MutableList<String>) {
        if (arguments.contains("-all")) {
            arguments.remove("-all")
            isAllListTask = true
        }
    }

    private fun userDialog(filterMode: FilterComposed, sequence: Sequence<Task>): ExecutionResult {
        val listTask = getFilteredList(sequence, filterMode).iterator()
        while (listTask.hasNext()) {
            printListTask(listTask.next())
            if (!listTask.hasNext()) {
                break
            }
            if (!isAllListTask) {
                val readUserAnswer = consoleReaderListTask.askUserForContinue()
                if (readUserAnswer == UserAction.STOP) {
                    break
                }
            }
        }
        isAllListTask = false
        return ExecutionResult.SUCCESS
    }

    private fun determineFilterMode(arguments: List<String>): FilterComposed? {
        val filterComposed = FilterComposed()
        arguments
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
        if (arguments.isEmpty()) {
            filterComposed.add(FilterAll())
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



