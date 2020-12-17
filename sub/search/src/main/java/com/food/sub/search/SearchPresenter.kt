package com.food.sub.search

import com.food.core.repository.recipe.RecipeRepository
import com.food.recipe.common.database.extension.distinctRecipes
import com.food.recipe.common.database.model.Recipe
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Created by mikekojansow on 29/09/20.
 * Senior Android Developer
 */
class SearchPresenter @Inject constructor(
    private val fragment: SearchFragment,
    private val recipeRepository: RecipeRepository
) {

    var currentRecipes: ArrayList<Recipe> = arrayListOf()
        private set

    fun searchRecipes(name: String, page: Int) {
        if (name.length < 3) return

        if (page == 1) {
            currentRecipes.clear()
            fragment.refreshItems()
        }


        CoroutineScope(Dispatchers.IO).async {
            val recipes = recipeRepository.searchRecipe(page, name)

            currentRecipes.distinctRecipes(recipes, onRefreshAdded = { position, recipe ->
                CoroutineScope(Dispatchers.Main).launch {
                    currentRecipes.add(recipe)
                    fragment.refreshItemInserted(position)
                }
            })
        }
    }

    fun onRecipeChangeEvent(recipe: Recipe) {
        var changesPosition = -1

        for ((position, currentRecipe) in currentRecipes.withIndex()) {
            if (currentRecipe.id == recipe.id) {
                changesPosition = position
            }
        }

        currentRecipes[changesPosition] = recipe

        fragment.refreshItemChanges(changesPosition)
    }
}