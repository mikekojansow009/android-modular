package com.food.recipe.common.database

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by mikekojansow on 18/08/20.
 * Senior Android Developer
 */
@Module
class DatabaseModule {

    @Provides
    fun getAppDatabase(context: Context): AppDatabase? {
        return AppDatabase.getDatabase(context)
    }

}