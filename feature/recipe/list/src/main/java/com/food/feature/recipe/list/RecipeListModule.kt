package com.food.feature.recipe.list

import com.food.core.network.module.NetworkModule
import com.food.core.repository.RepositoryModule
import com.food.core.repository.recipe.RecipeRepository
import dagger.Module
import dagger.Provides

/**
 * Created by mikekojansow on 05/09/20.
 * Senior Android Developer
 */
@Module(includes = [NetworkModule::class, RepositoryModule::class])
class RecipeListModule(val activity: RecipeListActivity) {

    @Provides
    fun getPresenter(recipeRepository: RecipeRepository): RecipeListPresenter {
        return RecipeListPresenter(activity, recipeRepository)
    }

}