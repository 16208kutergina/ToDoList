package ru.nsu.fit.todolist

import com.google.gson.Gson
import java.io.*
import java.util.*


class TaskFileManager(private var fileName: String) {
    private val gson = Gson()
    private lateinit var scanner: Scanner


    fun write(task: Task) {
        val fileWriter = FileWriter(fileName, true)
        val json = gson.toJson(task)
        fileWriter.write("$json\n")
        fileWriter.close()
    }

    fun openForRead() {
        val file = File(fileName)
        scanner = Scanner(file)
    }


    fun hasNextRead() : Boolean{
        return scanner.hasNext()
    }

    fun closeForRead() {
        scanner.close()
    }

    fun readNextListTask(count: Int): List<Task> {
        val listTasks = LinkedList<Task>()
        for (i in 0 until count) {
            val task = readNextTask() ?: break
            listTasks.add(task)
        }
        return listTasks
    }

    private fun readNextTask(): Task? {
        var task: Task? = null
        if(scanner.hasNext()) {
            val nextJson = scanner.nextLine()
            task = gson.fromJson(nextJson, Task::class.java)
        }
        return task
    }
}
