package com.food.core.model.extension

/**
 * Created by mikekojansow on 20/08/20.
 * Senior Android Developer
 */

fun Long.convertToSeconds(): Int {
    return (this / 1000).toInt()
}