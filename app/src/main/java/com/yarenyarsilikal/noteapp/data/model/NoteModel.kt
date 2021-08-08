package com.yarenyarsilikal.noteapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "note_table")
@Parcelize
data class NoteModel(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "imageUrl") var imageUrl: String = "",
    @ColumnInfo(name = "createdDate") var createdDate: String,
    @ColumnInfo(name = "isEdited") var isEdited: Boolean = false,
    @ColumnInfo(name = "editedDate") var editedDate: String = "",
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var primarykey: Int = 0
}