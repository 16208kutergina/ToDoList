package ru.nsu.fit.todolist

    fun main() {
        val fileName = "todo-list.json"
        val programStarter = ProgramStarter(fileName)
        programStarter.start()
    }
