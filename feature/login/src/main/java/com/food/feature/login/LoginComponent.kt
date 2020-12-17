package com.food.feature.login

import com.food.core.dependecy.di.module.ContextModule
import dagger.Component

/**
 * Created by mikekojansow on 01/08/20.
 * Senior Android Developer
 */
@Component(modules = [LoginModule::class, ContextModule::class])
interface LoginComponent {

    fun inject(activity: LoginActivity)

}