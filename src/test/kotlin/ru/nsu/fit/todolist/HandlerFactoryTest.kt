package ru.nsu.fit.todolist

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import ru.nsu.fit.todolist.handlers.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HandlerFactoryTest {
    private val handlerFactory = HandlerFactory()

    @Test
    fun runAddTest() {
        val command = Command("add", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(AddHandler::class.java, handler::class.java)
    }

    @Test
    fun runDeleteTest() {
        val command = Command("delete", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(DeleteHandler::class.java, handler::class.java)
    }

    @Test
    fun runDoneTest() {
        val command = Command("done", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(DoneHandler::class.java, handler::class.java)
    }

    @Test
    fun runListTest() {
        val command = Command("list", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(ListHandler::class.java, handler::class.java)
    }

    @Test
    fun runExitTest() {
        val command = Command("exit", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(ExitHandler::class.java, handler::class.java)
    }

    @Test
    fun runHelpTest() {
        val command = Command("help", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(HelpHandler::class.java, handler::class.java)
    }

    @Test
    fun runDefaultTest() {
        val command = Command("abrakadabra", "")
        val handler = this.handlerFactory.getHandler(command)
        assertEquals(HelpHandler::class.java, handler::class.java)
    }

}