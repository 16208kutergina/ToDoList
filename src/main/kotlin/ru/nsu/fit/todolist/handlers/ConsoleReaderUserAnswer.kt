package ru.nsu.fit.todolist.handlers

import java.util.*

class ConsoleReaderUserAnswer {

    fun askUserForContinue(): UserAction {
        println("Write \"next\" or \"stop\"")
        var nextLine = readLine()
        while (nextLine != "next" && nextLine != "stop") {
            println("Write \"next\" or \"stop\"")
            nextLine = readLine()
        }
        return if (nextLine == "next") {
            UserAction.NEXT
        } else {
            UserAction.STOP
        }
    }
}

enum class UserAction {
    NEXT,
    STOP
}