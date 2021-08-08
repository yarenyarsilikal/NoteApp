package com.yarenyarsilikal.noteapp.util

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.yarenyarsilikal.noteapp.R
import java.text.SimpleDateFormat
import java.util.*


fun getCurrentDateAndTime(): String =
    SimpleDateFormat.getDateInstance().format(Calendar.getInstance().time)

fun showSnackBar(view: View, text: String) {
    val snackBar = Snackbar.make(
        view, text,
        Snackbar.LENGTH_LONG
    )
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.light_blue))
    val textView =
        snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setTextColor(ContextCompat.getColor(view.context, R.color.light_darker_blue))
    snackBarView.elevation = 8f
    snackBar.show()
}

fun showAlertDialog(
    context: Context,
    message: String,
    yesClick: (DialogInterface) -> Unit,
    noClick: (DialogInterface) -> Unit
) {
    MaterialAlertDialogBuilder(context)
        .setMessage(message)
        .setNegativeButton(context.getString(R.string.discardProcess)) { dialog, which -> noClick(dialog) }
        .setPositiveButton(context.getString(R.string.continueProcess)) { dialog, which -> yesClick(dialog) }
        .show()
}

fun logInfo(tag: String, message: String) {
    Log.i(tag, message)
}
