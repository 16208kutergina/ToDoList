package ru.nsu.fit.todolist

enum class CommandExceptions(text: String) {
    SUCCESS("success"),

    UNKNOWN_COMMAND("unknown command"),
    FILE_PROBLEM("problem with file"),
    UNNAMED_TASK("task must have name")

}