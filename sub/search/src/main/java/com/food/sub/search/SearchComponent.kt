package com.food.sub.search

import com.food.core.dependecy.di.module.ContextModule
import dagger.Component

/**
 * Created by mikekojansow on 29/09/20.
 * Senior Android Developer
 */
@Component(modules = [ContextModule::class, SearchModule::class])
interface SearchComponent {
    fun inject(searchFragment: SearchFragment)
}