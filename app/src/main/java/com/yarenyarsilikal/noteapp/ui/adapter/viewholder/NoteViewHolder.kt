package com.yarenyarsilikal.noteapp.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.yarenyarsilikal.noteapp.data.model.NoteModel
import com.yarenyarsilikal.noteapp.databinding.ItemNoteBinding


class NoteViewHolder(private val binding: ItemNoteBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        noteModel: NoteModel,
        onMapClickListener: ((NoteModel) -> Unit)?
    ) {
        binding.model = noteModel
        itemView.setOnClickListener {
            if (onMapClickListener != null) {
                onMapClickListener(noteModel)
            }
        }
    }

}