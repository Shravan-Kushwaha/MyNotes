package com.example.mynotes.ui.activity

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityAddNoteBinding
import com.example.mynotes.databinding.DialogDateTimePickerBinding
import com.example.mynotes.ui.viemodels.AddNoteViewModel
import com.example.mynotes.utils.MyNotificationReceiver
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class AddNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNoteBinding
    lateinit var viewModel: AddNoteViewModel
    var timeStamp: Long = 0
    lateinit var tvTitle: String
    lateinit var tvBody: String
    var id: String = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUI()
        setClickListener()
        getBundleExtra()
    }

    private fun getBundleExtra() {
        if (intent.extras != null) {
            id = intent.extras!!.getString("id").toString()
            tvTitle = intent.extras!!.getString("title").toString()
            tvBody = intent.extras!!.getString("body").toString()
            binding.tvNoteTitle.setText(tvTitle)
            binding.tvNoteBody.setText(tvBody)
        }
    }

    override fun onPause() {
        super.onPause()
        insertNoteInRoom()
    }

    private fun setUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        viewModel = ViewModelProvider(this)[AddNoteViewModel::class.java]
    }

    private fun setClickListener() {
        binding.apply {
            headerBack.ivDrawerBackIcon.setOnClickListener { finish() }
            headerBack.ivBellIcon.setOnClickListener {
                setBottomSheet()
                setupDialog()
            }
        }
    }

    private fun setupDialog() {
        val dialog = Dialog(this@AddNoteActivity, R.style.PauseDialog)
        val dialogBinding: DialogDateTimePickerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this@AddNoteActivity),
            R.layout.dialog_date_time_picker,
            null,
            false
        )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.attributes = lp

        binding.cvBottomSheet.setOnClickListener {
            dialogBinding.cancelButton.setOnClickListener { v: View? -> dialog.dismiss() }

            dialogBinding.okButton.setOnClickListener {
                setNotification()
                dialog.dismiss()
            }

            dialogBinding.tvDate.setOnClickListener {
                setDatePicker(dialogBinding)
            }

            dialogBinding.tvTime.setOnClickListener {
                setTimePicker(dialogBinding)
            }
        }
        dialog.show()
    }

    private fun setNotification() {
        val selectedCalendar = Calendar.getInstance()
        /* selectedCalendar.set(Calendar.YEAR, year) // Set year from DatePicker
         selectedCalendar.set(Calendar.MONTH, month) // Set month from DatePicker
         selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth) // Set day from DatePicker
         selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay) // Set hour from TimePicker
         selectedCalendar.set(Calendar.MINUTE, minute) // Set minute from TimePicker*/

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyNotificationReceiver::class.java)
        intent.putExtra("notification_id", 1) // Unique ID for the notification
        val pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        timeStamp = System.currentTimeMillis() + 5000

// Schedule the notification
        alarmManager.set(AlarmManager.RTC, timeStamp, pendingIntent)
    }

    private fun setDatePicker(
        dialogBinding: DialogDateTimePickerBinding
    ) {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(supportFragmentManager, "DatePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
            val date = dateFormatter.format(Date(it))
            dialogBinding.tvDate.text = date.toString()
            timeStamp = 0
            timeStamp = it
        }
    }

    private fun setTimePicker(dialogBinding: DialogDateTimePickerBinding) {

        val materialTimePicker: MaterialTimePicker = MaterialTimePicker.Builder()
            .setTitleText("SELECT YOUR TIMING")
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .build()

        materialTimePicker.show(supportFragmentManager, "AddNoteActivity")

        materialTimePicker.addOnPositiveButtonClickListener {
            dialogBinding.tvTime.text = viewModel.formatDate(materialTimePicker)
        }
    }


    private fun setBottomSheet() {
        binding.cvBottomSheet.visibility = View.VISIBLE
        val arrivedDialogBehavior = BottomSheetBehavior.from(binding.cvBottomSheet)
        arrivedDialogBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun insertNoteInRoom() {
        if (binding.tvNoteTitle.text.toString().isNotEmpty() || binding.tvNoteBody.text.toString()
                .isNotEmpty()
        ) {

            /*  *//* if (intent.extras != null) {*//*
                viewModel.update(
                    id = id,
                    title = binding.tvNoteTitle.text.toString(),
                    body = binding.tvNoteBody.text.toString()
                )
            } else {*/
            viewModel.insert(
                id = id.toLong(),
                title = binding.tvNoteTitle.text.toString(),
                body = binding.tvNoteBody.text.toString()
            )
            // }
        }
    }
}