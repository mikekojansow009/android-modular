package com.food.feature.splash

import com.food.core.preferences.Pref
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by mikekojansow on 25/07/20.
 * Senior Android Developer
 */
class SplashPresenter @Inject constructor(var activity: SplashActivity) {

    fun navigateToNextPage() {
        GlobalScope.launch {
            delay(1500)

            Pref().token?.let {
                activity.navigateToHomePage()
            } ?: activity.navigateToLoginPage()
        }
    }

}