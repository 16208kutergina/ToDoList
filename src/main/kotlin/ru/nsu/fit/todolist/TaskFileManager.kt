package ru.nsu.fit.todolist

import com.google.gson.Gson
import java.io.*
import java.util.*


class TaskFileManager(private var fileName: String) {
    private val gson = Gson()
    private lateinit var scanner: Scanner

    private fun replaceFile(nameTmpFile: String) {
        val file = File(fileName)
        val tmpFile = File(nameTmpFile)
        file.delete()
        tmpFile.renameTo(file)
    }

    fun <T> write(task: Task, failReturn: T, success: T): T {
        var fileWriter: FileWriter? = null
        return try {
            fileWriter = FileWriter(fileName, true)
            val json = gson.toJson(task)
            fileWriter.write("$json\n")
            success
        } catch (e: IOException) {
            failReturn
        } finally {
            fileWriter?.close()
        }
    }

    fun <T> read(failReturn: T, callback: () -> T): T {
        return try {
            val file = File(fileName)
            if (!file.exists()) {
                file.createNewFile()
            }
            scanner = Scanner(file)
            callback()
        } catch (e: IOException) {
            failReturn
        } finally {
            scanner.close()
        }
    }

    fun getTaskSequence(): Sequence<Task> {
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


    fun <T> delete(listNumberTasks: List<Int>, failReturn: T, success: T) : T {
        val nameTmpFile = "tmp_$fileName"
        val executionStatus = writeNewFileWithoutDelTask(
            nameTmpFile,
            listNumberTasks,
            failReturn,
            success
        )
        replaceFile(nameTmpFile)
        return executionStatus
    }

    private fun <T> writeNewFileWithoutDelTask(
        nameTmpFile: String,
        listNumberTasks: List<Int>,
        failReturn: T,
        success: T
    ): T {
        return read(failReturn, {
            val tmpFileWriter = FileWriter(nameTmpFile, true)
            var counterTask = 0
            var nextTaskJson: String
            while (scanner.hasNext()) {
                nextTaskJson = scanner.nextLine()
                counterTask++
                if (listNumberTasks.contains(counterTask)) {
                    continue
                }
                tmpFileWriter.write("$nextTaskJson\n")
            }
            tmpFileWriter.close()
            success
        })
    }


    private fun <T> writeNewFileChangeStatus(
        nameTmpFile: String,
        listNumberTasks: List<Int>,
        status: StatusTask,
        failReturn: T,
        success: T
    ): T {
        return read(failReturn, {
            val tmpFileWriter = FileWriter(nameTmpFile, true)
            var counterTask = 0
            while (scanner.hasNext()) {
                counterTask++
                val nextTask = readNextTask()
                if (listNumberTasks.contains(counterTask)) {
                    nextTask?.status = status
                }
                val json = gson.toJson(nextTask)
                tmpFileWriter.write("$json\n")
            }
            tmpFileWriter.close()
            success
        })
    }

    fun <T> markDone(listNumberTasks: List<Int>, failReturn: T, success: T): T {
        val nameTmpFile = "tmp_$fileName"
        val executionStatus =
            writeNewFileChangeStatus(nameTmpFile, listNumberTasks, StatusTask.DONE, failReturn, success)
        replaceFile(nameTmpFile)
        return executionStatus
    }
}
