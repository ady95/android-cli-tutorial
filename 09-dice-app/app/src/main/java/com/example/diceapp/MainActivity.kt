package com.example.diceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DiceScreen()
                }
            }
        }
    }
}

/** 굴린 결과. 아직 굴리지 않았으면 null. */
private data class Roll(val first: Int, val second: Int) {
    val sum: Int get() = first + second
    val isDouble: Boolean get() = first == second
}

@Composable
fun DiceScreen(modifier: Modifier = Modifier) {
    var roll by remember { mutableStateOf<Roll?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val current = roll
        if (current == null) {
            Text(
                text = "주사위를 굴려 보세요",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        } else {
            Text(
                text = "${current.first} + ${current.second} = ${current.sum}",
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall
            )
            if (current.isDouble) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "더블!",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(onClick = { roll = Roll(Random.nextInt(1, 7), Random.nextInt(1, 7)) }) {
            Text(text = "굴리기", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DiceScreenPreview() {
    MaterialTheme {
        DiceScreen()
    }
}
