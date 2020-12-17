package com.food.core.model.extension

import android.content.Intent

/**
 * Created by mikekojansow on 08/08/20.
 * Senior Android Developer
 */

fun Intent.newTaskActivity() {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
}