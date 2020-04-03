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

    fun write(task: Task): ExecutionResult {
        var fileWriter: FileWriter? = null
        return try {
            fileWriter = FileWriter(fileName, true)
            val json = gson.toJson(task)
            fileWriter.write("$json\n")
            ExecutionResult.SUCCESS
        } catch (e: IOException) {
            ExecutionResult.FILE_PROBLEM
        } finally {
            fileWriter?.close()
        }
    }

    private fun getTaskSequence(): Sequence<Task> {
        return generateSequence {
            readNextTask()
        }
    }

    fun getTasks(block: (Sequence<Task>) -> ExecutionResult): ExecutionResult {
        return try {
            openScannerForRead()
            block(getTaskSequence())
            ExecutionResult.SUCCESS
        } catch (e: IOException) {
            ExecutionResult.FILE_PROBLEM
        } finally {
            closeScannerForRead()
        }
    }

    private fun closeScannerForRead() {
        scanner.close()
    }

    private fun openScannerForRead() {
        val file = File(fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        scanner = Scanner(file)
    }

    private fun readNextTask(): Task? {
        var task: Task? = null
        if (scanner.hasNext()) {
            val nextJson = scanner.nextLine()
            task = gson.fromJson(nextJson, Task::class.java)
        }
        return task
    }


    fun delete(listNumberTasks: List<Int>): ExecutionResult {
        val nameTmpFile = "tmp_$fileName"
        val executionStatus = writeNewFileWithoutDelTask(
            nameTmpFile,
            listNumberTasks
        )
        replaceFile(nameTmpFile)
        return executionStatus
    }

    private fun writeNewFileWithoutDelTask(
        nameTmpFile: String,
        listNumberTasks: List<Int>
    ): ExecutionResult {
        var tmpFileWriter: FileWriter? = null
        return try {
            tmpFileWriter = FileWriter(nameTmpFile, true)
            openScannerForRead()
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
            ExecutionResult.SUCCESS
        } catch (e: Exception) {
            ExecutionResult.FILE_PROBLEM
        } finally {
            closeScannerForRead()
            tmpFileWriter?.close()
        }
    }


    private fun writeNewFileChangeStatus(
        nameTmpFile: String,
        listNumberTasks: List<Int>,
        status: StatusTask
    ): ExecutionResult {
        var tmpFileWriter: FileWriter? = null
        return try {
            tmpFileWriter = FileWriter(nameTmpFile, true)
            openScannerForRead()
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
            ExecutionResult.SUCCESS
        } catch (e: Exception) {
            ExecutionResult.FILE_PROBLEM
        } finally {
            closeScannerForRead()
            tmpFileWriter?.close()
        }
    }

    fun markDone(listNumberTasks: List<Int>): ExecutionResult {
        val nameTmpFile = "tmp_$fileName"
        val executionStatus =
            writeNewFileChangeStatus(nameTmpFile, listNumberTasks, StatusTask.DONE)
        replaceFile(nameTmpFile)
        return executionStatus
    }
}
