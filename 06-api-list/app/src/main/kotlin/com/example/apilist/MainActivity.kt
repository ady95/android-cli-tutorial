package com.example.apilist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 화면이 가질 수 있는 세 가지 상태
sealed interface UiState {
    data object Loading : UiState
    data class Success(val posts: List<Post>) : UiState
    data class Error(val message: String) : UiState
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PostListScreen()
            }
        }
    }
}

@Composable
fun PostListScreen() {
    var state by remember { mutableStateOf<UiState>(UiState.Loading) }

    // 화면이 처음 만들어질 때 한 번만 실행되는 부수 효과
    LaunchedEffect(Unit) {
        state = try {
            UiState.Success(ApiClient.postApi.getPosts())
        } catch (e: Exception) {
            UiState.Error(e.message ?: "알 수 없는 오류")
        }
    }

    when (val s = state) {
        is UiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is UiState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("불러오기 실패: ${s.message}")
        }

        is UiState.Success -> LazyColumn(
            modifier = Modifier.fillMaxSize().safeDrawingPadding()
        ) {
            items(s.posts) { post ->
                Text(
                    text = "${post.id}. ${post.title}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
                HorizontalDivider()
            }
        }
    }
}
