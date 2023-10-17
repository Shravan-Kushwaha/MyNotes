package com.example.mynotes.ui.viemodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mynotes.data.local.database.MyDatabase
import com.example.mynotes.data.local.entity.Note
import com.example.mynotes.utils.Constants
import com.google.android.material.timepicker.MaterialTimePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(private val db: MyDatabase) : ViewModel() {
    fun insert(id:Long,title: String, body: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDAO().insert(
                    Note(
                        id = id, title = title, body = body
                    )
                )
            }

        } catch (e: Exception) {
            Log.e(Constants.TAG, "AddNoteViewModel insert: Exception ${e.message}")
        }
    }
    fun update(title: String, body: String,id:Long) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
              /*  db.noteDAO().update(
                    Note(
                        id= id,title = title, body = body
                    )
                )*/
            }

        } catch (e: Exception) {
            Log.e(Constants.TAG, "AddNoteViewModel insert: Exception ${e.message}")
        }
    }
    fun formatDate(mT: MaterialTimePicker): String {
        val pickedHour: Int = mT.hour
        val pickedMinute: Int = mT.minute

        val formattedTime: String = when {
            pickedHour > 12 -> {
                if (pickedMinute < 10) {
                    "${mT.hour - 12}:0${mT.minute} pm"
                } else {
                    "${mT.hour - 12}:${mT.minute} pm"
                }
            }

            pickedHour == 12 -> {
                if (pickedMinute < 10) {
                    "${mT.hour}:0${mT.minute} pm"
                } else {
                    "${mT.hour}:${mT.minute} pm"
                }
            }

            pickedHour == 0 -> {
                if (pickedMinute < 10) {
                    "${mT.hour + 12}:0${mT.minute} am"
                } else {
                    "${mT.hour + 12}:${mT.minute} am"
                }
            }

            else -> {
                if (pickedMinute < 10) {
                    "${mT.hour}:0${mT.minute} am"
                } else {
                    "${mT.hour}:${mT.minute} am"
                }
            }
        }
        return formattedTime
    }
}