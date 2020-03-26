package ru.nsu.fit.todolist

import java.io.InputStream
import java.util.*

class ConsoleReader {

    public fun run(inputStream: InputStream) {
        val scanner = Scanner(inputStream)
        val commandRunner = CommandRunner()
        var command: Command?
        do {
            val instructions = readLine(scanner)
            if (instructions.size < 1) {
                break
            } else {
                command = parseCommand(instructions)
                if (command == null) {
                    println("add instruction") // todo write instruction
                    continue
                }
                commandRunner.run(command) //todo may be should be enum
            }
        } while (command != null
            && command.command == "exit"
        )

    }

    private fun parseCommand(instructions: List<String>): Command? {
        return if (instructions.first().isNotEmpty()
            && instructions.size <= 2
        ) {
            Command(
                instructions.first(),
                instructions.last()
            )
        } else {
            null
        }
    }

    private fun readLine(scanner: Scanner): List<String> {
        return scanner.nextLine()
            .trim()
            .split(" ".toRegex(), 2)
    }

}