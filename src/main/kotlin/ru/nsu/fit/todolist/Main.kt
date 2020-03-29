package ru.nsu.fit.todolist

    fun main() {
        val inputStream = System.`in`
        val fileName = "todo-list.json"
        val programStarter = ProgramStarter(inputStream, fileName)
        programStarter.start()
    }
