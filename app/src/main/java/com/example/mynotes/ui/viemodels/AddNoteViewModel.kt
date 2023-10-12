package com.example.mynotes.ui.viemodels

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.example.mynotes.data.local.database.MyDatabase
import com.example.mynotes.data.local.entity.Note
import com.example.mynotes.databinding.DialogDateTimePickerBinding
import com.example.mynotes.utils.Constants
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(private val db: MyDatabase) : ViewModel() {
    fun upsert(title: String, body: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDAO().upsert(
                    Note(
                        title = title, body = body
                    )
                )
            }

        } catch (e: Exception) {
            Log.e(Constants.TAG, "AddNoteViewModel insert: Exception ${e.message}")
        }
    }

    fun formatDate(materialTimePicker: MaterialTimePicker): String {
        val pickedHour: Int = materialTimePicker.hour
        val pickedMinute: Int = materialTimePicker.minute

        val formattedTime: String = when {
            pickedHour > 12 -> {
                if (pickedMinute < 10) {
                    "${materialTimePicker.hour - 12}:0${materialTimePicker.minute} pm"
                } else {
                    "${materialTimePicker.hour - 12}:${materialTimePicker.minute} pm"
                }
            }

            pickedHour == 12 -> {
                if (pickedMinute < 10) {
                    "${materialTimePicker.hour}:0${materialTimePicker.minute} pm"
                } else {
                    "${materialTimePicker.hour}:${materialTimePicker.minute} pm"
                }
            }

            pickedHour == 0 -> {
                if (pickedMinute < 10) {
                    "${materialTimePicker.hour + 12}:0${materialTimePicker.minute} am"
                } else {
                    "${materialTimePicker.hour + 12}:${materialTimePicker.minute} am"
                }
            }

            else -> {
                if (pickedMinute < 10) {
                    "${materialTimePicker.hour}:0${materialTimePicker.minute} am"
                } else {
                    "${materialTimePicker.hour}:${materialTimePicker.minute} am"
                }
            }
        }
        return formattedTime
    }
}