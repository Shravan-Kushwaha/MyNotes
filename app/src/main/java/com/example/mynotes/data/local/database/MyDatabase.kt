package com.example.mynotes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynotes.data.local.dao.NoteDAO
import com.example.mynotes.data.local.entity.Note

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun noteDAO(): NoteDAO
}