package ru.nsu.fit.todolist

import java.io.InputStream
import java.util.*

class ProgramStarter(private val inputStream: InputStream, fileName: String = "todo-list.json") {
    private val commandRunner = CommandRunner(fileName)
    val scanner = Scanner(inputStream)

    fun start() {
        var command: Command? = null
        do {
            val instructions = myReadLine(scanner) ?: continue
            if(instructions.isEmpty()){
                continue
            }
            command = parseCommand(instructions)
            if (command == null) {
                println("add instruction") // todo write instruction
                continue
            }
            val executionResult = commandRunner.run(command)
            if(executionResult != ExecutionResult.SUCCESS) {
                println(executionResult)
            }
        } while (command == null
            || command.command != "exit"
        )
    }

    private fun parseCommand(instructions: List<String>): Command? {
        return when {
            instructions.size == 2 -> Command(
                instructions.first(),
                instructions.last()
            )
            instructions.size == 1 -> Command(
                instructions.first(),
                ""
            )
            else -> null
        }
    }

    private fun myReadLine(scanner: Scanner): List<String>? {
        print("write command:>")
        return scanner.nextLine()
            .trim()
            .split(" ".toRegex(), 2)
    }

}