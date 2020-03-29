package ru.nsu.fit.todolist.handlers

import java.util.*

class ConsoleReaderUserAnswer {

    fun askUserForContinue(scanner: Scanner): UserAction {
        println("Write \"next\" or \"stop\"")
        var nextLine = scanner.nextLine()
        while (nextLine != "next" && nextLine != "stop") {
            println("Write \"next\" or \"stop\"")
            nextLine = scanner.nextLine()
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