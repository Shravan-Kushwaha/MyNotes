package com.example.mynotes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.mynotes.data.local.entity.Note

@Dao
interface NoteDAO {
    @Upsert
    suspend fun upsert(note: Note)

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<Note>
}