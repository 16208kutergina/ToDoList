package ru.nsu.fit.todolist.handlers

import ru.nsu.fit.todolist.StatusTask
import ru.nsu.fit.todolist.Task
import java.util.*


abstract class Filter {
    abstract fun isAccept(task: Task): Boolean
}

class FilterComposed : Filter() {
    private val listFilters = HashSet<Filter>()

    override fun isAccept(task: Task): Boolean {
        return listFilters.any {
            it.isAccept(task)
        }
    }

    fun add(filter: Filter) {
        listFilters.add(filter)
    }
}

class FilterAll : Filter() {
    override fun isAccept(task: Task): Boolean = true
}

class FilterStatus(private val statusTask: StatusTask) : Filter() {
    override fun isAccept(task: Task): Boolean = task.status == statusTask
}

