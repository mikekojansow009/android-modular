package com.food.feature.splash

import dagger.Module
import dagger.Provides

/**
 * Created by mikekojansow on 29/07/20.
 * Senior Android Developer
 */
@Module
class SplashModule(var activity: SplashActivity) {

    @Provides
    fun getPresenter(): SplashPresenter {
        return SplashPresenter(activity)
    }

}