package com.yarenyarsilikal.noteapp.util.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

fun Fragment.navigate(resId : Int){
    NavHostFragment.findNavController(this)
        .navigate(resId)
}

fun Fragment.navigate(resId : Int, args : Bundle?){
    NavHostFragment.findNavController(this)
        .navigate(resId, args)
}