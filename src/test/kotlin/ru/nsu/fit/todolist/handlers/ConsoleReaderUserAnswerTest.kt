package ru.nsu.fit.todolist.handlers

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ConsoleReaderUserAnswerTest {
    private val consoleReaderUserAnswer = ConsoleReaderUserAnswer()
    private val originalIn = System.`in`

    @Test
    fun askUserForContinueNext() {
        val byteInputStream = "next\n".byteInputStream()
        System.setIn(byteInputStream)
        val userAction = consoleReaderUserAnswer.askUserForContinue()
        byteInputStream.close()
        assertEquals(UserAction.NEXT, userAction)
    }

    @Test
    fun askUserForContinueEnter() {
        val byteInputStream = "\n".byteInputStream()
        System.setIn(byteInputStream)
        val userAction = consoleReaderUserAnswer.askUserForContinue()
        byteInputStream.close()
        assertEquals(UserAction.NEXT, userAction)
    }

    @Test
    fun askUserForContinueStop() {
        val byteInputStream = "stop\n".byteInputStream()
        System.setIn(byteInputStream)
        val userAction = consoleReaderUserAnswer.askUserForContinue()
        byteInputStream.close()
        assertEquals(UserAction.STOP, userAction)
    }

    @Test
    fun askUserForContinueRandomText() {
        val byteInputStream = "random\nstop\n".byteInputStream()
        System.setIn(byteInputStream)
        val userAction = consoleReaderUserAnswer.askUserForContinue()
        byteInputStream.close()
        assertEquals(UserAction.STOP, userAction)
    }

    @AfterAll
    fun setOriginalIn(){
        System.setIn(originalIn)
    }
}