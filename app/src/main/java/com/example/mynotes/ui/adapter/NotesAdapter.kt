package com.example.mynotes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.data.local.entity.Note
import com.example.mynotes.databinding.ItemNotesListBinding

class NotesAdapter(val list: List<Note>) : RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {


    inner class MyViewHolder(val recyclerViewDataBInding: ItemNotesListBinding) :
        RecyclerView.ViewHolder(recyclerViewDataBInding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_notes_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.recyclerViewDataBInding.tvNoteTitle.text = list[position].title
        holder.recyclerViewDataBInding.tvNoteBody.text = list[position].body
    }
}