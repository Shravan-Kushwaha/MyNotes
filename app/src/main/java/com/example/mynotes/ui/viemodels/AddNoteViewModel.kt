package com.example.mynotes.ui.viemodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mynotes.data.local.database.MyDatabase
import com.example.mynotes.data.local.entity.Note
import com.example.mynotes.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(private val db: MyDatabase) : ViewModel() {
    fun insert(title: String, body: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDAO().insert(
                    Note(
                       title = title, body = body
                    )
                )
            }

        } catch (e: Exception) {
            Log.e(Constants.TAG, "AddNoteViewModel insert: Exception ${e.message}")
        }
    }
}