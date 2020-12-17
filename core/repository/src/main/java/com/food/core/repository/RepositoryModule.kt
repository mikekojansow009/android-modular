package com.food.core.repository

import com.food.core.network.module.NetworkModule
import com.food.core.repository.recipe.RecipeRepository
import com.food.core.repository.user.UserRepository
import com.food.recipe.common.database.AppDatabase
import com.food.recipe.common.database.DatabaseModule
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by mikekojansow on 16/08/20.
 * Senior Android Developer
 */
@Module(includes = [NetworkModule::class, DatabaseModule::class])
class RepositoryModule {

    @Provides
    fun getUserRepository(retrofit: Retrofit, database: AppDatabase?): UserRepository {
        return UserRepository(retrofit, database)
    }

    @Provides
    fun getRecipeRepository(retrofit: Retrofit, database: AppDatabase?): RecipeRepository {
        return RecipeRepository(retrofit, database)
    }
}