package com.example.mynotes.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.base.BaseViewHolder
import com.example.mynotes.data.local.entity.Note
import com.example.mynotes.databinding.ItemNotesListBinding
import com.example.mynotes.ui.activity.AddNoteActivity
import com.example.mynotes.ui.interfaces.OnClick


class NotesAdapter(private val list: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.MyViewHolder>(),OnClick {
    var isCheck = false
    var isLongClickEnable = false
    var itemSelectCount = 0
    var ID = 0

    inner class MyViewHolder(val recyclerViewDataBinding: ItemNotesListBinding) :
        BaseViewHolder(recyclerViewDataBinding.root) {

        override fun onBind(position: Int) {
            recyclerViewDataBinding.apply {
                viewModel = list[position]
                ID = list[position].id.toInt()
                clMain.setOnLongClickListener {
                    isCheck = !isCheck
                    if (isCheck) itemSelectCount++ else itemSelectCount--
                    cbCheck.isChecked = isCheck
                    cbCheck.visibility = View.VISIBLE
                    isLongClickEnable = true
                    true
                }
            }

        }
    }

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
        holder.onBind(position)
        holder.recyclerViewDataBinding.apply {
            clMain.setOnClickListener {
                if (isLongClickEnable) {
                    if (itemSelectCount > 0) {
                        if (cbCheck.isChecked) {
                            isCheck = false
                            cbCheck.isChecked = isCheck
                            cbCheck.visibility = View.GONE
                            itemSelectCount--

                        } else {
                            onClick(ID)
                            itemSelectCount++
                            isCheck = true
                            cbCheck.isChecked = isCheck
                            cbCheck.visibility = View.VISIBLE
                        }
                    } else {
                        isLongClickEnable = false
                        holder.itemView.context.startActivity(
                            Intent(
                                holder.itemView.context,
                                AddNoteActivity::class.java
                            )
                                .putExtra("title", tvNoteTitle.text.toString())
                                .putExtra("body", tvNoteBody.text.toString())
                                .putExtra("id", ID.toString())
                        )
                    }

                } else {
                    holder.itemView.context.startActivity(
                        Intent(
                            holder.itemView.context,
                            AddNoteActivity::class.java
                        )
                            .putExtra("title", tvNoteTitle.text.toString())
                            .putExtra("body", tvNoteBody.text.toString())
                            .putExtra("id", ID.toString())
                    )
                }
            }
        }
    }

    override fun onClick(id: Int) {

    }
}