package ru.nsu.fit.todolist

import com.google.gson.Gson
import java.io.*
import java.util.*
import kotlin.collections.ArrayList


class TaskFileManager(private var fileName: String = "todo-list.json") {
    private val gson = Gson()
    private lateinit var scanner: Scanner


    fun write(task: Task) {
        val fileWriter = FileWriter(fileName, true)
        val json = gson.toJson(task)
        fileWriter.write("$json\n")
        fileWriter.close()
    }

    fun openForRead() {
        scanner = Scanner(fileName)
    }

    fun closeForRead() {
        scanner.close()
    }

    fun readNext(count: Int): Array<Task?> {
        val listTasks = arrayOfNulls<Task>(size = count)
        for (i in 0 until count) {
            if (!scanner.hasNext()) {
                break
            }
            val nextJson = scanner.nextLine()
            val task = gson.fromJson(nextJson, Task::class.java)
            listTasks[i] = task
        }
        return listTasks
    }
}

fun main() {
    val split = "add 5 6 7 3".split(" ".toRegex(), 2)
    println(split.toString())
}