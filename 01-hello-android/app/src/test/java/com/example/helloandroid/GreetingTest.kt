package com.example.helloandroid

import org.junit.Assert.assertEquals
import org.junit.Test

class GreetingTest {
    @Test
    fun greeting_isCorrect() {
        val greeting = "Hello, Android CLI!"
        assertEquals("Hello, Android CLI!", greeting)
    }
}
