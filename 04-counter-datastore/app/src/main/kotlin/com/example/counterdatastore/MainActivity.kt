package com.example.counterdatastore

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// 앱 전체에서 하나만 존재하는 DataStore 인스턴스 (파일명: settings)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

// 저장할 값의 키 (타입별 키 함수: int/string/boolean...)
val COUNT_KEY = intPreferencesKey("count")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CounterScreen(dataStore)
            }
        }
    }
}

@Composable
fun CounterScreen(dataStore: DataStore<Preferences>) {
    val scope = rememberCoroutineScope()

    // 저장소의 값 변화를 Flow로 구독 → 상태로 변환
    val count by dataStore.data
        .map { prefs -> prefs[COUNT_KEY] ?: 0 }
        .collectAsState(initial = 0)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Count: $count",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                dataStore.edit { prefs ->
                    prefs[COUNT_KEY] = (prefs[COUNT_KEY] ?: 0) + 1
                }
            }
        }) {
            Text("Count Up")
        }
    }
}
