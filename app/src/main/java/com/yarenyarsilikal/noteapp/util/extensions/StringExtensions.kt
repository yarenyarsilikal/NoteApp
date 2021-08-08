package com.yarenyarsilikal.noteapp.util.extensions

import android.webkit.URLUtil


fun String.isValidURL(): Boolean = URLUtil.isValidUrl(this)


