package ru.nsu.fit.todolist.handlers

val helpText = """
    help - print instruction            
    add <name task> - add new task
    done <number_task1 number_task2 ...> - mark tasks DONE
    delete <number_task1 number_task2 ...> - delete tasks
    list        - print tasks without filter
        add next flags:
         -done  - print DONE tasks
         -todo  - print TODO tasks
                next - next part(10) tasks
                stop - return to main console
         -all   - print all list tasks
    exit - exit from application
            """.trimIndent()

