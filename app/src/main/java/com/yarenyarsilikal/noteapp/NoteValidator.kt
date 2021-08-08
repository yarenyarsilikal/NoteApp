package com.yarenyarsilikal.noteapp

import com.yarenyarsilikal.noteapp.util.extensions.isValidURL

object NoteValidator {

    fun isValidUrl(url: String): Boolean =
        if (url.isEmpty()) true else url.isValidURL()


    fun isValidNote(title: String, description: String): Boolean {
        return title.isEmpty().not() && description.isEmpty().not()
    }
}