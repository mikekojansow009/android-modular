package com.food.core.dependecy.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by mikekojansow on 26/07/20.
 * Senior Android Developer
 */
@Module
class AppModule(private val app: Application) {

    @Singleton
    @Provides
    fun provideApp(): Application = app

    @Provides
    fun provideAppContext(): Context = app.applicationContext

}