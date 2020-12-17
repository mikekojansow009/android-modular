package com.food.sub.search

import com.food.core.network.module.NetworkModule
import com.food.core.repository.RepositoryModule
import com.food.core.repository.recipe.RecipeRepository
import dagger.Module
import dagger.Provides

/**
 * Created by mikekojansow on 29/09/20.
 * Senior Android Developer
 */
@Module(includes = [RepositoryModule::class, NetworkModule::class])
class SearchModule(private val fragment: SearchFragment) {

    @Provides
    fun getPresenter(recipeRepository: RecipeRepository): SearchPresenter {
        return SearchPresenter(fragment, recipeRepository)
    }

}