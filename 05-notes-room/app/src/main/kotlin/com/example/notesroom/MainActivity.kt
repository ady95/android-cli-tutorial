package com.example.notesroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Room
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes.db"
        ).build()

        setContent {
            MaterialTheme {
                NotesScreen(db.noteDao())
            }
        }
    }
}

@Composable
fun NotesScreen(dao: NoteDao) {
    val scope = rememberCoroutineScope()
    val notes by dao.getAll().collectAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            scope.launch {
                dao.insert(Note(text = "메모 ${notes.size + 1}"))
            }
        }) {
            Text("메모 추가")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(notes) { note ->
                Text(
                    text = "${note.id}. ${note.text}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
