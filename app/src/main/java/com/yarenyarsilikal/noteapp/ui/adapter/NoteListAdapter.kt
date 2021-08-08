package com.yarenyarsilikal.noteapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yarenyarsilikal.noteapp.ui.adapter.viewholder.NoteViewHolder
import com.yarenyarsilikal.noteapp.data.model.NoteModel
import com.yarenyarsilikal.noteapp.databinding.ItemNoteBinding

class NoteListAdapter(
    private var list: List<NoteModel>?,
    private val onMapClickListener: ((NoteModel) -> Unit)?
) :
    RecyclerView.Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int = list?.size!!

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        list?.get(position)?.let { holder.bind(it, onMapClickListener) }
    }
}