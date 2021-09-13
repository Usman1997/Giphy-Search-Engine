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

/**
 * Extension function to show keyboard
 */
fun View?.showKeyboard() {
    if (this == null) return
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Extension function to hide keyboard
 */
fun View?.hideKeyboard() {
    if (this == null) return
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * This function is to limit the API request when the user is typing. We don't want to make
 * API request for every character typed by user in search bar so we have setup a time span in
 * which an API request is made when the user is typing. For this purpose, we have used debouce here
 * because debouce operator filters out item that are rapidly followed by another emitted item.
 */
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

/**
 * Extension function to show snackbar
 */
fun View.showSnackBar(message: String){
    Snackbar.make(this,message,Snackbar.LENGTH_LONG).show()
}
