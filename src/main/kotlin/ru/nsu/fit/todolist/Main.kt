package ru.nsu.fit.todolist

    fun main() {
        val fileName = "todo-list.json"
        val taskFileManager = TaskFileManager(fileName)
        val handlerFactory = HandlerFactory()
        val programStarter = ProgramStarter(taskFileManager, handlerFactory)
        programStarter.start()
    }
