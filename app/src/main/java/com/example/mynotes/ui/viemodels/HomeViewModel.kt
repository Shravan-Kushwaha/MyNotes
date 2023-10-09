package com.example.mynotes.ui.viemodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.room.Insert
import com.example.mynotes.data.local.database.MyDatabase
import com.example.mynotes.data.local.entity.Note
import com.example.mynotes.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val db: MyDatabase) : ViewModel() {
    suspend fun getAllNote(): List<Note> {
        var allNote: List<Note> = ArrayList()
        try {
            val job = CoroutineScope(Dispatchers.IO).async {
                allNote = db.noteDAO().getAllNotes()
            }
            job.await()
        } catch (e: Exception) {
            Log.e(Constants.TAG, "AddNoteViewModel getAllNote: Exception ${e.message}")
        }
        return allNote
    }
}