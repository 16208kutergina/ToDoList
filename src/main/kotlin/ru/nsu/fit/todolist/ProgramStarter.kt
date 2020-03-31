package ru.nsu.fit.todolist

import java.io.InputStream
import java.util.*

class ProgramStarter(inputStream: InputStream, fileName: String = "todo-list.json") {
    private val commandRunner = CommandRunner(fileName)
    private val scanner = Scanner(inputStream)

    fun start() {
        while(true){
            val instructions = myReadLine(scanner) ?: continue
            if (instructions.first().isEmpty()) {
                continue
            }
            val command = parseCommand(instructions)
            val executionResult = commandRunner.run(command)
            if(executionResult == ExecutionResult.EXIT){
                break
            }
            if (executionResult != ExecutionResult.SUCCESS) {
                println(executionResult.text)
            }
        }
        scanner.close()
    }

    private fun parseCommand(instructions: List<String>): Command {
        return if (instructions.size == 1) {
            Command(
                instructions.first(),
                ""
            )
        } else {
            Command(
                instructions.first(),
                instructions.last()
            )
        }
    }

    private fun myReadLine(scanner: Scanner): List<String>? {
        print("write command:>")
        return scanner.nextLine()
            .trim()
            .split(" ".toRegex(), 2)
            .map { it.trim() }
    }

}