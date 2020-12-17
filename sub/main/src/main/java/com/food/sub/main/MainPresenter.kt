package com.food.sub.main

import com.food.recipe.common.database.model.Recipe
import com.food.sub.main.model.HomeRecipe
import javax.inject.Inject

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
class MainPresenter @Inject constructor(
    private val fragment: MainFragment,
    private val repository: MainRepository
) {

    var currentHomeRecipes = ArrayList<HomeRecipe>()
        private set

    private var lastPositionRecipes = currentHomeRecipes.size + 1

    fun loadHomeRecipes() {
        println("Loading recipes...")
        repository.getHomeRecipe { homeRecipes ->
            distinctHomeRecipes(homeRecipes)

            val endPosition = currentHomeRecipes.size

            fragment.refreshData()

            lastPositionRecipes = endPosition
        }
    }

    fun reloadHomeRecipes(recipeChanges: Recipe) {
        var parentPositionChanges = -1
        var childPositionChanges = -1

        for ((parentPosition, homeRecipe) in currentHomeRecipes.withIndex()) {
            for ((childPosition, recipe) in homeRecipe.recipes.withIndex()) {
                if (recipe.id == recipeChanges.id) {
                    parentPositionChanges = parentPosition
                    childPositionChanges = childPosition
                    break
                }
            }
        }

        if (parentPositionChanges != -1 && childPositionChanges != -1) {
            println("Change recipe and reload")
            val parentHomeRecipe = currentHomeRecipes[parentPositionChanges]
            parentHomeRecipe.recipes.removeAt(childPositionChanges)
            parentHomeRecipe.recipes.add(childPositionChanges, recipeChanges)

            fragment.refreshHome(parentPositionChanges, parentPositionChanges)
        }
    }

    private fun distinctHomeRecipes(homeRecipes: ArrayList<HomeRecipe>) {
        for (homeRecipe in homeRecipes) {
            if (!currentHomeRecipes.contains(homeRecipe)) currentHomeRecipes.add(homeRecipe)
        }
    }

}