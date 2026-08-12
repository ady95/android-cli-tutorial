package com.example.helloandroid

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this).apply {
            text = "Hello, Android CLI!"
            textSize = 28f
            gravity = Gravity.CENTER
        }
        setContentView(textView)
    }
}
