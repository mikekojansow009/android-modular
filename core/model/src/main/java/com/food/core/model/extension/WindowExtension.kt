package com.food.core.model.extension

import android.os.Build
import android.view.View
import android.view.Window

/**
 * Created by mikekojansow on 28/07/20.
 * Senior Android Developer
 */

fun Window.changeStatusBar(color: Int, isLightStatusBar: Boolean = true) {
    if(Build.VERSION.SDK_INT >= 23) {
        decorView.systemUiVisibility = if(isLightStatusBar) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else 0
        statusBarColor = color
    }
}