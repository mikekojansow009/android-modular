package com.food.core.utility

import android.util.Log
import com.food.core.firebase.crashlytic.CrashlyticConfig
import kotlin.Exception

/**
 * Created by mikekojansow on 28/07/20.
 * Senior Android Developer
 */
object Log {

    fun printLog(title: String, message: String, exception: Exception? = null) {
        if (BaseInfo.isProductionBuild) {
            val currentException: Exception = exception ?: Exception("Title : $title - Message : $message")

            CrashlyticConfig.printLog(currentException)
            return
        }

        Log.e(title, message)
    }

}