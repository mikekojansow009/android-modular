package com.food.sub.main.content

import com.food.core.repository.RepositoryModule
import com.food.core.repository.recipe.RecipeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
@Module(includes = [RepositoryModule::class])
class ContentModule @Inject constructor(private val contentFragment: ContentFragment) {

    @Provides
    fun getPresenter(recipeRepository: RecipeRepository): ContentPresenter {
        return ContentPresenter(contentFragment, recipeRepository)
    }

}