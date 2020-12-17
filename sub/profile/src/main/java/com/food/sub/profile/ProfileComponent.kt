package com.food.sub.profile

import com.food.core.dependecy.di.module.ContextModule
import dagger.Component

/**
 * Created by mikekojansow on 12/08/20.
 * Senior Android Developer
 */
@Component(modules = [ProfileModule::class, ContextModule::class])
interface ProfileComponent {

    fun inject(fragment: ProfileFragment)

}