package ru.nsu.fit.todolist

interface Handler {
    fun handle(command: Command, taskFileManager: TaskFileManager): CommandExceptions
}