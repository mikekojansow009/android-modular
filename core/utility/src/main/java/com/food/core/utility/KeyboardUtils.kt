package com.food.core.utility

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

/**
 * Created by mikekojansow on 01/08/20.
 * Senior Android Developer
 */
object KeyboardUtils {

    fun getNavigationButtonsHeight(context: Context): Int {
        val resourceId: Int = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")

        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun hideKeyboard(activity: FragmentActivity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}