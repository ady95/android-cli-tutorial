package com.example.apilist

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// 응답 JSON의 필드와 이름이 같으면 자동으로 채워진다
data class Post(
    val id: Int,
    val title: String,
    val body: String,
)

// API 명세를 인터페이스로 선언 - 구현은 Retrofit이 만든다
interface PostApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}

// Retrofit 인스턴스 (연습용 공개 API: JSONPlaceholder)
object ApiClient {
    val postApi: PostApi = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PostApi::class.java)
}
