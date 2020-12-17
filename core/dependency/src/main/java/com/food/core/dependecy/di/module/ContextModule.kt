package com.food.core.dependecy.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by mikekojansow on 26/07/20.
 * Senior Android Developer
 */
@Module
class ContextModule(private val context: Context) {

    @Provides
    fun provideContext(): Context = context
}