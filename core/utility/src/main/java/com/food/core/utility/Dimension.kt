package com.food.core.utility

import android.util.DisplayMetrics
import android.util.Size
import androidx.fragment.app.FragmentActivity

/**
 * Created by mikekojansow on 24/09/20.
 * Senior Android Developer
 */
object Dimension {

    fun getDeviceScreen(activity: FragmentActivity): Size {
        val displayMetrics = DisplayMetrics()

        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        return Size(width, height)
    }

}