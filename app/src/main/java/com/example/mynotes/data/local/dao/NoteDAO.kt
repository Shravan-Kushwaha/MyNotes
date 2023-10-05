package com.example.mynotes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mynotes.data.local.entity.Note

@Dao
interface NoteDAO {
    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<Note>
}