package com.food.feature.home

import com.food.core.dependecy.di.module.ContextModule
import dagger.Component

/**
 * Created by mikekojansow on 16/08/20.
 * Senior Android Developer
 */
@Component(modules = [HomeModule::class, ContextModule::class])
interface HomeComponent {

    fun inject(homeActivity: HomeActivity)

}