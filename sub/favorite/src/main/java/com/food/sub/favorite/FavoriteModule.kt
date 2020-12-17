package com.food.sub.favorite

import com.food.core.network.module.NetworkModule
import com.food.core.repository.RepositoryModule
import com.food.core.repository.recipe.RecipeRepository
import dagger.Module
import dagger.Provides

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
@Module(includes = [RepositoryModule::class, NetworkModule::class])
class FavoriteModule(private val fragment: FavoriteFragment) {

    @Provides
    fun getPresenter(repository: RecipeRepository): FavoritePresenter {
        return FavoritePresenter(fragment, repository)
    }

}