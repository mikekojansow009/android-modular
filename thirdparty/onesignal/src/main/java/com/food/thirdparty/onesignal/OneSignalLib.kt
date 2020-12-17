package com.food.thirdparty.onesignal

import android.content.Context
import com.food.core.preferences.Pref
import com.onesignal.OneSignal

/**
 * Created by mikekojansow on 27/09/20.
 * Senior Android Developer
 */
object OneSignalLib {

    fun initiate(context: Context, onesignalId: String) {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        OneSignal.initWithContext(context)
        OneSignal.setAppId(onesignalId)
        OneSignal.setNotificationOpenedHandler(OneSignalNotificationOpen(context))

        OneSignal.addSubscriptionObserver {
            Pref().oneSignalId = it.to.userId
        }
    }

}