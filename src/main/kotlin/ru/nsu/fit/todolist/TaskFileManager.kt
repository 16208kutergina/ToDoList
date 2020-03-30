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

    //---------------------------------------------------------------------
    fun openForRead() {
        val file = File(fileName)
        scanner = Scanner(file)
    }

    fun getTaskSeq(): Sequence<Task> {
        return generateSequence {
            readNextTask()
        }
    }

    private fun readNextTask(): Task? {
        var task: Task? = null
        if (scanner.hasNext()) {
            val nextJson = scanner.nextLine()
            task = gson.fromJson(nextJson, Task::class.java)
        }
        return task
    }

    fun closeForRead() {
        scanner.close()
    }
    //-----------------------------------------------------------------

    fun delete(listNumberTasks: List<Int>) {
        val nameTmpFile = "tmp_$fileName"
        writeFileWithoutDelete(nameTmpFile, listNumberTasks)
        val file = File(fileName)
        val tmpFile = File(nameTmpFile)
        file.delete()
        tmpFile.renameTo(file)
    }

    private fun writeFileWithoutDelete(nameTmpFile: String, listNumberTasks: List<Int>) {
        openForRead()
        val tmpfileWriter = FileWriter(nameTmpFile, true)
        var counterTask = 0
        var nextTaskJson = ""
        while (scanner.hasNext()) {
            nextTaskJson = scanner.nextLine()
            counterTask++
            if (listNumberTasks.contains(counterTask)) {
                continue
            }
            tmpfileWriter.write("$nextTaskJson\n")
        }
        closeForRead()
        tmpfileWriter.close()
    }
}
