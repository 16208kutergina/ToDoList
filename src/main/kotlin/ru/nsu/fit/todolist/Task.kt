package ru.nsu.fit.todolist

data class Task(
    var status: StatusTask,
    var nameTask: String
){
    override fun toString(): String {
        return "$nameTask  $status"
    }
}


enum class StatusTask(sign: String) {
    TODO("*"),
    DONE("v")
}
