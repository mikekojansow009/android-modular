package com.food.core.firebase.crashlytic

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics

/**
 * Created by mikekojansow on 27/07/20.
 * Senior Android Developer
 */
object CrashlyticConfig {

    fun setupCrashlyticAndCollection(enabled: Boolean, context: Context) {
        FirebaseApp.initializeApp(context)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(enabled)
    }

    fun setUserId(userId: String, name: String) {
        FirebaseCrashlytics.getInstance().setUserId(userId)
        FirebaseCrashlytics.getInstance().setCustomKey("Name", name)
    }

    fun printLog(exception: Exception) {
        FirebaseCrashlytics.getInstance().recordException(exception)
    }
}