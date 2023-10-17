package com.example.mynotes.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.mynotes.data.local.entity.Note

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)
    @Query("SELECT * FROM note")
    fun getAllNotes(): LiveData<List<Note>>
    @Query("SELECT * FROM note WHERE id = :id")
    fun findById(id: Int): Note?
    @Delete
    suspend fun delete(note: Note)
}