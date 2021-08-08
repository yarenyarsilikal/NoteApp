package com.yarenyarsilikal.noteapp

import com.yarenyarsilikal.noteapp.data.model.NoteModel
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class ValidatorTest {

    private lateinit var noteModel1: NoteModel
    private lateinit var noteModel2: NoteModel
    private lateinit var noteModel3: NoteModel
    private lateinit var noteModel4: NoteModel

    @Before
    fun prepareData() {
        println("Before ")
        noteModel1 = NoteModel("title", "", "", "", false, "")
        noteModel2 = NoteModel("", "", "", "", false, "")
        noteModel3 = NoteModel("", "description", "", "", false, "")
        noteModel4 = NoteModel("title", "", "upload", "", false, "")
    }


    //2 icon görünür yeşil run test tir
    @Test
    fun test_invalid_desc_return_false() {
        val result = NoteValidator.isValidNote(noteModel1.title, noteModel1.description)
        assertFalse(result)
    }

    @Test
    fun test_invalid_desc_and_title_return_false() {
        val result = NoteValidator.isValidNote(noteModel2.title, noteModel2.description)
        assertFalse(result)
    }

    @Test
    fun test_invalid_title_return_false() {
        val result = NoteValidator.isValidNote(noteModel3.title, noteModel3.description)
        assertFalse(result)
    }

    @Test
    fun test_invalid_url_return_true() {
        val result = NoteValidator.isValidUrl(noteModel4.imageUrl)
        print(result)
        assertFalse(result)
    }
}