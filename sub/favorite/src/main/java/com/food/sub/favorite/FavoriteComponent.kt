package com.food.sub.favorite

import com.food.core.dependecy.di.module.ContextModule
import com.food.core.repository.RepositoryModule
import dagger.Component

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
@Component(modules = [ContextModule::class, FavoriteModule::class])
interface FavoriteComponent {
    fun inject(fragment: FavoriteFragment)
}