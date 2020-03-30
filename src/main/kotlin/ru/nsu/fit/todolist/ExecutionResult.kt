package ru.nsu.fit.todolist

enum class ExecutionResult(val text: String) {
    SUCCESS("success"),
    EXIT("exit"),

    UNKNOWN_COMMAND("unknown command"),
    FILE_PROBLEM("problem with file"),
    UNNAMED_TASK("task must have name"),
    UNKNOWN_MODE_SORT("unknown mode of sort"),
    WRONG_FORMAT_ARGUMENTS("wrong format arguments")

}