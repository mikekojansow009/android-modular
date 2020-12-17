package com.food.core.model.extension

import java.util.*

/**
 * Created by mikekojansow on 20/08/20.
 * Senior Android Developer
 */
fun getCurrentTime(): Int {
    return Calendar.getInstance().timeInMillis.convertToSeconds()
}