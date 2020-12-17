package com.food.recipe

import android.app.Application
import com.food.core.dependecy.di.component.DaggerAppComponent
import com.food.core.firebase.crashlytic.CrashlyticConfig
import com.food.core.preferences.Pref
import com.food.core.utility.BaseInfo
import com.food.thirdparty.onesignal.OneSignalLib

/**
 * Created by mikekojansow on 24/07/20.
 * Senior Android Developer
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        CrashlyticConfig.setupCrashlyticAndCollection(!BuildConfig.DEBUG, this)

        BaseInfo.baseUrl = BuildConfig.BASE_API_URL
        BaseInfo.versionName = BuildConfig.VERSION_NAME
        BaseInfo.versionCode = BuildConfig.VERSION_CODE

        DaggerAppComponent.builder().build().inject(this)

        Pref.initiate(this)

        OneSignalLib.initiate(this, BuildConfig.ONE_SIGNAL_ID)
    }
}