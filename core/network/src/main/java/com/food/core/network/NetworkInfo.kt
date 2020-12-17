package com.food.core.network

import com.food.core.preferences.Pref
import com.food.core.utility.BaseInfo

/**
 * Created by mikekojansow on 26/07/20.
 * Senior Android Developer
 */
internal object NetworkInfo {
    private val pref = Pref()

    fun getHeaders(): HashMap<String, String> {
        val header = HashMap<String, String>()


        if (!pref.token?.accessToken?.token.isNullOrEmpty()) header["Authorization"] = "Bearer ${pref.token?.accessToken?.token}"

        header["DeviceData"] = BaseInfo.deviceData
        header["VersionCode"] = BaseInfo.versionCode.toString()
        header["VersionName"] = BaseInfo.versionName
        header["DeviceType"] = BaseInfo.deviceType
        header["DeviceId"] = BaseInfo.deviceId()

        return header
    }

    const val SERVER_ERROR = 500
    const val UNAUTHORIZE = 401
    const val UPGRADE_REQUIRED = 426
    const val TIMEOUT: Long = 20
}