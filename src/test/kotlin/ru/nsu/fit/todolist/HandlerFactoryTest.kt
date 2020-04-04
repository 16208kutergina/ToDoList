package ru.nsu.fit.todolist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ru.nsu.fit.todolist.handlers.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HandlerFactoryTest {
    private val handlerFactory = HandlerFactory

    @Test
    fun addTest() {
        val command = Command("add", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(AddHandler::class.java, handler::class.java)
    }

    @Test
    fun deleteTest() {
        val command = Command("delete", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(DeleteHandler::class.java, handler::class.java)
    }

    @Test
    fun doneTest() {
        val command = Command("done", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(DoneHandler::class.java, handler::class.java)
    }

    @Test
    fun listTest() {
        val command = Command("list", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(ListHandler::class.java, handler::class.java)
    }

    @Test
    fun exitTest() {
        val command = Command("exit", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(ExitHandler::class.java, handler::class.java)
    }

    @Test
    fun helpTest() {
        val command = Command("help", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(HelpHandler::class.java, handler::class.java)
    }

    @Test
    fun defaultTest() {
        val command = Command("abrakadabra", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(HelpHandler::class.java, handler::class.java)
    }

}