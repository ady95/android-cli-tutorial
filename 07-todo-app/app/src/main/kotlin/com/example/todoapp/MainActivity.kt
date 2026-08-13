package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.room.Room
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext, TodoDatabase::class.java, "todos.db"
        ).build()

        setContent {
            MaterialTheme {
                TodoScreen(db.todoDao())
            }
        }
    }
}

@Composable
fun TodoScreen(dao: TodoDao) {
    val scope = rememberCoroutineScope()
    val todos by dao.getAll().collectAsState(initial = emptyList())
    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("할 일") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (input.isNotBlank()) {
                        val text = input   // 코루틴 실행 전에 값을 확정 (아래 관찰 포인트 참조)
                        scope.launch { dao.insert(Todo(text = text)) }
                        input = ""
                    }
                }
            ) { Text("추가") }
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(todos, key = { it.id }) { todo ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = todo.done,
                        onCheckedChange = { checked ->
                            scope.launch { dao.update(todo.copy(done = checked)) }
                        }
                    )
                    Text(
                        text = todo.text,
                        style = MaterialTheme.typography.titleMedium,
                        textDecoration = if (todo.done) TextDecoration.LineThrough else null,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = {
                        scope.launch { dao.delete(todo) }
                    }) { Text("삭제") }
                }
            }
        }
    }
}
