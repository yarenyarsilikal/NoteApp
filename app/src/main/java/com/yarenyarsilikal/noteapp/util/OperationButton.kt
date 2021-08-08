package com.yarenyarsilikal.noteapp.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.appcompat.widget.LinearLayoutCompat
import com.yarenyarsilikal.noteapp.R
import com.yarenyarsilikal.noteapp.data.model.NoteDetail
import com.yarenyarsilikal.noteapp.util.extensions.hide
import com.yarenyarsilikal.noteapp.util.extensions.show

class OperationButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private var mode: Int = 0
    private lateinit var higher: (OperationButtonType) -> Unit

    private var createButton: ImageButton
    private var editButton: ImageButton
    private var deleteButton: ImageButton

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.operations_button, this, true)

        createButton = findViewById(R.id.imageButtonCreate)
        editButton = findViewById(R.id.imageButtonEdit)
        deleteButton = findViewById(R.id.imageButtonDelete)

    }

    private fun setVisibilityOfButton(mode: Int) {
        when (mode) {
            NoteDetail.CREATE.ordinal -> createMode()
            NoteDetail.VIEW.ordinal -> viewMode()
        }
        createButton.setOnClickListener { higher(OperationButtonType.CREATE) }
        editButton.setOnClickListener { higher(OperationButtonType.EDIT) }
        deleteButton.setOnClickListener { higher(OperationButtonType.DELETE) }
    }

    private fun createMode() {
        createButton.show()
        editButton.hide()
        deleteButton.hide()
        invalidate()

    }

    private fun viewMode() {
        createButton.hide()
        editButton.show()
        deleteButton.show()
        invalidate()

    }

    fun setMode(mode: Int) {
        this.mode = mode
        setVisibilityOfButton(mode)
        invalidate()
    }

    fun setHigher(higher: (OperationButtonType) -> Unit) {
        this.higher = higher
    }

    enum class OperationButtonType {
        CREATE,
        EDIT,
        DELETE
    }
}