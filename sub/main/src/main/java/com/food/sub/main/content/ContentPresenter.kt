package com.food.sub.main.content

import com.food.core.model.data.MarkFavoriteResponse
import com.food.core.repository.recipe.RecipeRepository
import com.food.recipe.common.database.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
class ContentPresenter @Inject constructor(
    private val fragment: ContentFragment,
    private val recipeRepository: RecipeRepository
) {
    private var recipe: Recipe? = null

    private fun onMarkSuccess(favoriteResponse: MarkFavoriteResponse) {
        recipe?.isFavorite = favoriteResponse.isFavorite

        if (favoriteResponse.isFavorite) fragment.playAnimationLikeRecipe()
        else fragment.playAnimationDislikeRecipe()

        fragment.changeTotalLikeRecipe(favoriteResponse.likes)
    }

    private fun onMarkFailed() {

    }

    private fun refreshRecipeView() {
        recipe?.let { recipe ->
            fragment.setRecipeView(recipe)
        }
    }

    fun validateRecipe(recipe: Recipe?) {
        if (this.recipe != null && this.recipe?.id != recipe?.id) return

        this.recipe = recipe

        refreshRecipeView()
    }

    fun markFavorite(recipeId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            recipeRepository.markAsFavorite(recipeId, onSuccess = ::onMarkSuccess, onFailed = ::onMarkFailed)
        }
    }
}