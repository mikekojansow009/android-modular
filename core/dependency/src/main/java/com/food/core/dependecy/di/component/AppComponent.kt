package com.food.core.dependecy.di.component

import android.app.Application
import com.food.core.dependecy.di.module.AppModule
import com.food.core.dependecy.di.module.ContextModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by mikekojansow on 26/07/20.
 * Senior Android Developer
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(app: Application)

}