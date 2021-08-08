@file:Suppress("UselessCallOnNotNull")

package com.yarenyarsilikal.noteapp.ui.bindingadapters

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yarenyarsilikal.noteapp.data.model.NoteModel
import com.yarenyarsilikal.noteapp.ui.adapter.NoteListAdapter
import com.yarenyarsilikal.noteapp.util.OperationButton
import com.yarenyarsilikal.noteapp.util.extensions.isValidURL


@BindingAdapter("adapter", "adapterClick")
fun RecyclerView.setRecyclerViewAdapter(
    list: List<NoteModel>?,
    higher: ((NoteModel) -> Unit)?
) {
    if (this.adapter == null) {
        list?.let {
            this.adapter = NoteListAdapter(list, higher)
        }
    }
}

@BindingAdapter("addNoteClick")
fun ImageButton.addNoteClick(
    higher: (() -> Unit)?
) {
    this.setOnClickListener { higher?.invoke() }
}


@BindingAdapter("loadImageViewWithUrl")
fun ImageView.setImageViewWithUrl(url: String?) {
    if (url.isNullOrEmpty().not())
        Glide.with(context).load(url).fitCenter()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(this)
}

@BindingAdapter("viewVisibility")
fun View.setViewVisibility(value: String?) {
    this.visibility = if (value.isNullOrEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter("imageVisibility")
fun ImageView.setimageVisibility(value: String) {
    this.visibility = if (value.isNullOrEmpty() || value.isValidURL().not()) View.GONE else View.VISIBLE
}


@BindingAdapter("setNoteMode", "setOperationClick")
fun OperationButton.setOperationButtonConfig(
    value: Int,
    higher: ((OperationButton.OperationButtonType) -> Unit)
) {
    this.setMode(value)
    this.setHigher(higher)

}