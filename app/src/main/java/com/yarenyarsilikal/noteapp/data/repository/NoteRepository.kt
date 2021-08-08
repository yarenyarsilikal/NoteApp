package com.yarenyarsilikal.noteapp.data.repository

import com.yarenyarsilikal.noteapp.data.db.NoteDao
import com.yarenyarsilikal.noteapp.data.model.NoteModel
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    fun createNote(noteModel: NoteModel) =
        noteDao.createNote(noteModel)

    fun editNote(noteModel: NoteModel) =
        noteDao.editNote(noteModel)

    fun deleteNote(noteModel: NoteModel) =
        noteDao.deleteNote(noteModel)

    fun getNotes(): List<NoteModel> = noteDao.getNotes()
}