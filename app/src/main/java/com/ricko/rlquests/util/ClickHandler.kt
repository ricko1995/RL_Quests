package com.ricko.rlquests.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class ClickHandler {
    fun hideKeyboard(v: View){
        val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
        println("sdfasdfasdfa")
    }
}