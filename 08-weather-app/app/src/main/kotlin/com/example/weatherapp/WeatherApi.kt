package com.example.weatherapp

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Open-Meteo 응답 중 현재 날씨 부분만 선언
data class WeatherResponse(
    @SerializedName("current_weather") val currentWeather: CurrentWeather,
)

data class CurrentWeather(
    val temperature: Double,      // 섭씨
    val windspeed: Double,        // km/h
    @SerializedName("weathercode") val weatherCode: Int,
)

interface WeatherApi {
    @GET("v1/forecast?current_weather=true")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): WeatherResponse
}

// 무료 공개 날씨 API (인증 키 불필요): https://open-meteo.com
object WeatherClient {
    val api: WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)
}

// WMO 날씨 코드를 사람이 읽을 표현으로 (대표 코드만)
fun weatherCodeToText(code: Int): String = when (code) {
    0 -> "맑음"
    1, 2 -> "대체로 맑음"
    3 -> "흐림"
    45, 48 -> "안개"
    in 51..67 -> "비"
    in 71..77 -> "눈"
    in 80..82 -> "소나기"
    in 95..99 -> "뇌우"
    else -> "코드 $code"
}
