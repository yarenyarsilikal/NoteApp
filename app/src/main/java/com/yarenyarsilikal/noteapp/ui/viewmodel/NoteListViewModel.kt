package com.yarenyarsilikal.noteapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yarenyarsilikal.noteapp.data.model.NoteDetail
import com.yarenyarsilikal.noteapp.data.model.NoteModel
import com.yarenyarsilikal.noteapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(private val noteRepository: NoteRepository) :
    ViewModel() {

    var noteList: MutableLiveData<List<NoteModel>> = MutableLiveData()

    init {
        loadNotes()
    }

    private val _selectedItem: MutableLiveData<Event<NoteModel?>> = MutableLiveData()
    val selectedItem: LiveData<Event<NoteModel?>>
        get() = _selectedItem

    val createNewNote = MutableLiveData<Event<Int>>()

    val noteOnClick: (NoteModel) -> Unit = {
        _selectedItem.value = Event(it)
    }

    val createNoteClick: () -> Unit = {
        createNewNote.value = Event(NoteDetail.CREATE.ordinal)
    }

    fun getNotes(): MutableLiveData<List<NoteModel>> {
        return noteList
    }

    private fun loadNotes() {
        noteList.value = noteRepository.getNotes()
    }
}