package com.example.mynotes.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.mynotes.R
import com.example.mynotes.data.local.entity.Note
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.ui.adapter.NotesAdapter
import com.example.mynotes.ui.viemodels.AddNoteViewModel
import com.example.mynotes.ui.viemodels.HomeViewModel
import com.example.mynotes.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var viewModel: HomeViewModel
    lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUI()
        setClickListener()
    }

    override fun onResume() {
        super.onResume()
        setNoteRecyclerView()
    }

    private fun setUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private fun setNoteRecyclerView() {
        val layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        binding.rvNotes.layoutManager = layoutManager
        try {
            CoroutineScope(Dispatchers.Main).launch {
                val job = CoroutineScope(Dispatchers.IO).async {
                    val list = viewModel.getAllNote()
                    adapter = NotesAdapter(
                        list
                    )
                }
                job.await()
                binding.rvNotes.adapter = adapter
            }
        } catch (e: Exception) {
            Log.e(Constants.TAG, "HomeActivity onResume: Exception ${e.message}")
        }
    }

    private fun setClickListener() {
        binding.footer.ivAddNoteIcon.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddNoteActivity::class.java
                )
            )
        }
    }

}