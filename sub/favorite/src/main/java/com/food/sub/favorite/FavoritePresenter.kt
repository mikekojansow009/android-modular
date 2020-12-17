package com.food.sub.favorite

import com.food.core.repository.recipe.RecipeRepository
import com.food.recipe.common.database.extension.distinctRecipes
import com.food.recipe.common.database.model.Recipe
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
class FavoritePresenter @Inject constructor(
    private val fragment: FavoriteFragment,
    private val repository: RecipeRepository
) {

    var currentRecipes = ArrayList<Recipe>()
        private set

    fun fetchFavoriteRecipes(page: Int, onFinished: () -> Unit) {
        if (page == 1) fragment.showLoadingRecipes()

        CoroutineScope(Dispatchers.IO).async {
            val recipes = repository.getFavoriteRecipes(page)

            currentRecipes.distinctRecipes(recipes, onRefreshAdded = { position, recipe ->
                CoroutineScope(Dispatchers.Main).launch {
                    currentRecipes.add(recipe)
                    fragment.refreshRecipes(position)
                }
            })

            withContext(Dispatchers.Main) {
                onFinished()
                fragment.hideLoadingRecipes()

                if (currentRecipes.isEmpty()) {
                    fragment.hideLoadingRecipes()
                    fragment.showEmptyView()
                } else {
                    fragment.hideEmptyView()
                }
            }
        }
    }

    fun markRecipe(recipeId: String) {
        CoroutineScope(Dispatchers.IO).async {
            repository.markAsFavorite(recipeId, onSuccess = {

            }, onFailed = {})
        }
    }

    fun onRecipeChangeEvent(recipe: Recipe) {
        var hasNoRecipe = true
        var removePosition = -1

        for ((position, currentRecipe) in currentRecipes.withIndex()) {
            if (currentRecipe.id == recipe.id) {
                hasNoRecipe = false
                removePosition = position
            }
        }

        if (hasNoRecipe) currentRecipes.add(0, recipe)
        else currentRecipes.removeAt(removePosition)
    }

}