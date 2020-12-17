package com.food.core.base.extension

import android.view.Window
import android.view.WindowManager

/**
 * Created by mikekojansow on 01/08/20.
 * Senior Android Developer
 */

fun Window.flagNoLimits() {
    this.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}