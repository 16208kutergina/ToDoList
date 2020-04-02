package ru.nsu.fit.todolist.handlers

class ConsoleReaderUserAnswer {

    fun askUserForContinue(): UserAction {
        println("Write \"next\" or \"stop\"")
        var nextLine = readLine()
        while (nextLine != "next" && nextLine != "stop" && nextLine != "") {
            println("Write \"next\" or \"stop\"")
            nextLine = readLine()?:break
        }
        return if (nextLine == "stop") {
            UserAction.STOP
        } else {
            UserAction.NEXT
        }
    }
}

enum class UserAction {
    NEXT,
    STOP
}