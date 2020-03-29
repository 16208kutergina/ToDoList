package ru.nsu.fit.todolist

enum class ExecutionResult(text: String) {
    SUCCESS("success"),

    UNKNOWN_COMMAND("unknown command"),
    FILE_PROBLEM("problem with file"),
    UNNAMED_TASK("task must have name"),
    UNKNOWN_MODE_SORT("unknown mode of sort")

}