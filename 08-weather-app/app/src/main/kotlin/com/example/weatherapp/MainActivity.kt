package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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

// 도시 목록 (고정 좌표 - 위치 권한 없이 동작)
enum class City(val label: String, val lat: Double, val lon: Double) {
    SEOUL("서울", 37.57, 126.98),
    BUSAN("부산", 35.18, 129.08),
    JEJU("제주", 33.50, 126.53),
}

sealed interface WeatherState {
    data object Loading : WeatherState
    data class Success(val weather: CurrentWeather) : WeatherState
    data class Error(val message: String) : WeatherState
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                WeatherScreen()
            }
        }
    }
}

@Composable
fun WeatherScreen() {
    var city by remember { mutableStateOf(City.SEOUL) }
    var state by remember { mutableStateOf<WeatherState>(WeatherState.Loading) }

    // city가 바뀔 때마다 다시 실행된다 (LaunchedEffect의 key)
    LaunchedEffect(city) {
        state = WeatherState.Loading
        state = try {
            WeatherState.Success(
                WeatherClient.api.getCurrentWeather(city.lat, city.lon).currentWeather
            )
        } catch (e: Exception) {
            WeatherState.Error(e.message ?: "알 수 없는 오류")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            City.entries.forEach { c ->
                Button(
                    onClick = { city = c },
                    enabled = city != c,
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) { Text(c.label) }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        when (val s = state) {
            is WeatherState.Loading -> CircularProgressIndicator()

            is WeatherState.Error -> Text("불러오기 실패: ${s.message}")

            is WeatherState.Success -> {
                Text(
                    text = "${city.label} ${s.weather.temperature}°C",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = weatherCodeToText(s.weather.weatherCode) +
                        " · 바람 ${s.weather.windspeed} km/h",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
