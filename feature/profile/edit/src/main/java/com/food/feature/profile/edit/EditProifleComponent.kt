package com.food.feature.profile.edit

import com.food.core.dependecy.di.module.ContextModule
import dagger.Component

/**
 * Created by mikekojansow on 22/08/20.
 * Senior Android Developer
 */
@Component(modules = [EditProfileModule::class, ContextModule::class])
interface EditProifleComponent {
    fun inject(activity: EditProfileActivity)
}