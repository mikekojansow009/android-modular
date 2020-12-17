package com.food.core.model.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by mikekojansow on 30/09/20.
 * Senior Android Developer
 */

/**
 * Should add another listener later
 */
fun EditText.onTextChanged(onAfterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            editable?.let {
                onAfterTextChanged(it.toString())
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    })
}