package ru.nsu.fit.todolist

data class Task(
    var status: StatusTask,
    var nameTask: String
)

enum class StatusTask {
    TODO,
    DONE
}
