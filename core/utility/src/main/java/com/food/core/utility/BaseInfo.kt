package com.food.core.utility

import android.os.Build
import com.food.core.preferences.Pref
import com.google.gson.Gson

/**
 * Created by mikekojansow on 02/11/20.
 * Senior Android Developer
 */
object BaseInfo {
    var baseUrl = ""

    var isProductionBuild = false

    var versionName = ""

    var versionCode = 1

    fun deviceId(): String {
        return Pref().uuid
    }

    val deviceType: String
        get() {
            return "ANDROID"
        }

    val deviceData: String
        get() {
            val device = HashMap<String, String>()

            device["Manufacture"] = Build.MANUFACTURER
            device["Model"] = Build.MODEL
            device["Brand"] = Build.BRAND
            device["OsVersion"] = Build.VERSION.RELEASE
            device["ApiVersion"] = Build.VERSION.SDK_INT.toString()

            return Gson().toJson(device)
        }
}