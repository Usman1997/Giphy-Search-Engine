/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun View?.showKeyboard() {
    if (this == null) return
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View?.hideKeyboard() {
    if (this == null) return
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun EditText.initTextChangeListener(
    scope: CoroutineScope,
    onChanged: (String) -> Unit
) {
    var debounceJob: Job? = null

    addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            debounceJob?.cancel()
            debounceJob = scope.launch {
                delay(Constants.SEARCH_TIME_DELAY)
                onChanged(p0.toString())
            }
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {}

    })
}

fun View.showSnackBar(message: String){
    Snackbar.make(this,message,Snackbar.LENGTH_LONG).show()
}
