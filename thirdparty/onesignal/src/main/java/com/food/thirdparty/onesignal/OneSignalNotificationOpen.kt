package com.food.thirdparty.onesignal

import android.content.Context
import com.food.core.utility.Actions
import com.google.gson.Gson
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal

/**
 * Created by mikekojansow on 08/10/20.
 * Senior Android Developer
 */
class OneSignalNotificationOpen(private val context: Context): OneSignal.OSNotificationOpenedHandler {
    override fun notificationOpened(result: OSNotificationOpenedResult?) {
        if (result == null) return

        val additionalData = result.notification.rawPayload

        if (additionalData != null) {
            val data = Gson().fromJson(additionalData, OneSignalData::class.java)

            when (data.type) {
                DataType.RECIPE -> {
                    Actions.navigateToRecipeDetailFromNotification(context, data.id)
                }
            }
        }
    }
}