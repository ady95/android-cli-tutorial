package com.example.claudefirst

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val countText = TextView(this).apply {
            text = "Count: $count"
            textSize = 24f
            gravity = Gravity.CENTER
        }

        val countButton = Button(this).apply {
            text = "Count Up"
            setOnClickListener {
                count++
                countText.text = "Count: $count"
            }
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            addView(countText)
            addView(countButton)
        }
        setContentView(layout)
    }
}
