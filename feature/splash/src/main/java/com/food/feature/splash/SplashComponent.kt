package com.food.feature.splash

import dagger.Component

/**
 * Created by mikekojansow on 29/07/20.
 * Senior Android Developer
 */
@Component(modules = [SplashModule::class])
interface SplashComponent {

    fun inject(activity: SplashActivity)

}