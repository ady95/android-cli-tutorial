package com.example.notesroom

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

// 테이블 정의: notes 테이블의 한 행 = Note 객체 하나
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
)

// 데이터 접근 방법 선언: SQL은 컴파일 시점에 검증된다
@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAll(): Flow<List<Note>>
}

// 데이터베이스 본체: 엔티티 목록과 버전을 선언
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
