package com.food.feature.recipe.detail

import com.food.core.network.module.NetworkModule
import com.food.core.repository.RepositoryModule
import com.food.core.repository.recipe.RecipeRepository
import dagger.Module
import dagger.Provides

/**
 * Created by mikekojansow on 30/08/20.
 * Senior Android Developer
 */
@Module(includes = [NetworkModule::class, RepositoryModule::class])
class RecipeDetailModule(private val dialog: RecipeDetailActivity) {

    @Provides
    fun getPresenter(recipeRepository: RecipeRepository): RecipeDetailPresenter {
        return RecipeDetailPresenter(dialog, recipeRepository)
    }
}