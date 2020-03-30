package ru.nsu.fit.todolist

data class Task(
    var status: StatusTask,
    var nameTask: String
){
    override fun toString(): String {
        return "$status: $nameTask"
    }
}


enum class StatusTask {
    TODO,
    DONE
}
