package com.yarenyarsilikal.noteapp.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarenyarsilikal.noteapp.NoteValidator
import com.yarenyarsilikal.noteapp.data.model.NoteModel
import com.yarenyarsilikal.noteapp.data.model.ValidErrorType
import com.yarenyarsilikal.noteapp.data.repository.NoteRepository
import com.yarenyarsilikal.noteapp.util.OperationButton
import com.yarenyarsilikal.noteapp.util.getCurrentDateAndTime
import com.yarenyarsilikal.noteapp.util.logInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(private val noteRepository: NoteRepository) :
    ViewModel() {

    val noteTitle: MutableLiveData<String> = MutableLiveData("")
    val noteDescription: MutableLiveData<String> = MutableLiveData("")
    val noteImageURL: MutableLiveData<String?> = MutableLiveData("")

    var noteModel: NoteModel? = null

    private val _noteMode: MutableLiveData<Int> = MutableLiveData(null)
    val noteMode: LiveData<Int>
        get() = _noteMode

    private val _clickedCreateOperationButton: MutableLiveData<Event<Int>> = MutableLiveData()
    val clickedCreateOperationButton: LiveData<Event<Int>>
        get() = _clickedCreateOperationButton

    private val _clickedEditOrDeleteOperationButton: MutableLiveData<Int> = MutableLiveData()
    val clickedEditOrDeleteOperationButton: LiveData<Int>
        get() = _clickedEditOrDeleteOperationButton

    val createItemOnClick: (OperationButton.OperationButtonType) -> Unit = {
        noteModel!!.imageUrl = noteImageURL.value.toString()
        noteDescription.value?.let { desc ->
            noteModel!!.description = desc
        }
        noteTitle.value?.let { title ->
            noteModel!!.title = title
        }

        val isValidNote = NoteValidator.isValidNote(noteModel!!.title, noteModel!!.description)
        val isValidUrl = NoteValidator.isValidUrl(noteModel!!.imageUrl)

        when (it) {
            OperationButton.OperationButtonType.CREATE -> {
                noteModel!!.createdDate = getCurrentDateAndTime()
                when {
                    isValidNote.not() -> {
                        _clickedCreateOperationButton.value = Event(ValidErrorType.INVALIDNOTE.errorCode)
                    }
                    isValidUrl.not() -> {
                        _clickedCreateOperationButton.value = Event(ValidErrorType.INVALIDURL.errorCode)
                        noteModel!!.imageUrl = ""
                    }
                    else -> {
                        noteModel?.let { noteModel ->
                            viewModelScope.launch(Dispatchers.IO) {
                                noteRepository.createNote(noteModel)
                                logInfo("noteOperation", "$noteModel is inserted on db.")
                            }
                            _clickedCreateOperationButton.value = Event(OperationButton.OperationButtonType.CREATE.ordinal)
                        }

                    }
                }
            }

            OperationButton.OperationButtonType.EDIT -> {
                when {
                    isValidNote.not() -> {
                        _clickedCreateOperationButton.value = Event(ValidErrorType.INVALIDNOTE.errorCode)
                    }
                    isValidUrl.not() -> {
                        _clickedCreateOperationButton.value = Event(ValidErrorType.INVALIDURL.errorCode)
                        noteModel!!.imageUrl = ""
                    }
                    else -> {
                        _clickedEditOrDeleteOperationButton.value = OperationButton.OperationButtonType.EDIT.ordinal
                    }
                }
            }

            OperationButton.OperationButtonType.DELETE -> {
                _clickedEditOrDeleteOperationButton.value = OperationButton.OperationButtonType.DELETE.ordinal
            }
        }
    }

    fun editNote() {
        if (!noteModel!!.isEdited) {
            noteModel!!.isEdited = true
        }
        noteModel!!.editedDate = getCurrentDateAndTime()
        noteModel?.let { it ->
            viewModelScope.launch(Dispatchers.IO) {
                noteRepository.editNote(it)
                logInfo("noteOperation", "$noteModel is edited on db.")
            }
        }
        _clickedCreateOperationButton.value =Event(OperationButton.OperationButtonType.EDIT.ordinal)
    }

    fun deleteNote() {
        noteModel?.let { noteModel ->
            viewModelScope.launch(Dispatchers.IO) {
                noteRepository.deleteNote(noteModel)
                logInfo("noteOperation", "$noteModel is deleted from db.")
            }
        }
        _clickedCreateOperationButton.value =
            Event(OperationButton.OperationButtonType.DELETE.ordinal)
    }

    fun setUI(arguments: Bundle?) {
        _noteMode.value = arguments?.getInt("argsDetailViewType")
        arguments?.getParcelable<NoteModel>("argNoteModel")?.let {
            noteModel = it
            noteTitle.value = it.title
            noteDescription.value = it.description
            noteImageURL.value = it.imageUrl
        }
        if (noteModel == null) {
            noteModel = NoteModel("", "", "", "", false, "")
        }
        logInfo("noteModel", "$noteModel : ${this.javaClass.name}")
    }


}
