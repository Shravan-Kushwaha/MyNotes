package com.example.mynotes.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.base.BaseViewHolder
import com.example.mynotes.data.local.entity.Note
import com.example.mynotes.databinding.ItemNotesListBinding
import com.example.mynotes.ui.activity.AddNoteActivity


class NotesAdapter(private val list: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {
    var isCheck = false
    var isLongClickEnable = false
    var itemSelectCount = 0

    inner class MyViewHolder(val recyclerViewDataBinding: ItemNotesListBinding) :
        BaseViewHolder(recyclerViewDataBinding.root) {

        override fun onBind(position: Int) {
            recyclerViewDataBinding.apply {
                viewModel = list[position]
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
                            itemSelectCount++
                            isCheck = true
                            cbCheck.isChecked = isCheck
                            cbCheck.visibility = View.VISIBLE
                        }
                    }

                } else {
                    holder.itemView.context.startActivity(
                        Intent(
                            holder.itemView.context,
                            AddNoteActivity::class.java
                        )
                    )
                }
            }
        }
    }
}