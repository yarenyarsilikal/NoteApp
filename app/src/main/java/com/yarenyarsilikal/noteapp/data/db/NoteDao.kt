package com.yarenyarsilikal.noteapp.data.db

import androidx.room.*
import com.yarenyarsilikal.noteapp.data.model.NoteModel

@Dao
interface NoteDao {
    @Insert
    fun createNote(note: NoteModel): Long

    @Update
    fun editNote(note: NoteModel): Int

    @Delete
    fun deleteNote(note: NoteModel): Int

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getNotes(): List<NoteModel>
}