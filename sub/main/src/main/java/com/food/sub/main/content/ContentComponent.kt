package com.food.sub.main.content

import com.food.core.dependecy.di.module.ContextModule
import dagger.Component

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
@Component(modules = [ContextModule::class, ContentModule::class])
interface ContentComponent {

    fun inject(fragment: ContentFragment)

}