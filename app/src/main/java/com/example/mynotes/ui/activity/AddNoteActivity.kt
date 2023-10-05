package com.example.mynotes.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mynotes.R
import com.example.mynotes.data.local.database.MyDatabase
import com.example.mynotes.databinding.ActivityAddNoteBinding
import com.example.mynotes.ui.viemodels.AddNoteViewModel
import com.example.mynotes.ui.viemodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNoteBinding
    lateinit var viewModel: AddNoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUI()
        setClickListener()

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
        binding.headerBack.ivDrawerBackIcon.setOnClickListener { finish() }
    }

    private fun insertNoteInRoom() {
        viewModel.insert(
            binding.tvNoteTitle.text.toString(),
            binding.tvNoteBody.text.toString()
        )
    }

}