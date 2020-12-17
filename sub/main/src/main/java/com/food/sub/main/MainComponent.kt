package com.food.sub.main

import com.food.core.dependecy.di.module.ContextModule
import dagger.Component

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
@Component(modules = [MainModule::class, ContextModule::class])
interface MainComponent {

    fun inject(mainFragment: MainFragment)

}