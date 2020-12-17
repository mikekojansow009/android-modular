package com.food.recipe.common.database.extension

import com.food.recipe.common.database.model.Recipe

/**
 * Created by mikekojansow on 30/09/20.
 * Senior Android Developer
 */

fun ArrayList<Recipe>.distinctRecipes(newRecipes: ArrayList<Recipe>, onRefreshAdded: (Int, Recipe) -> Unit) {
    if (isEmpty()) {
        newRecipes.forEachIndexed { index, recipe ->
            onRefreshAdded(index, recipe)
        }

        return
    }

    for (newRecipe in newRecipes) {
        val existingRecipe = find { it.id == newRecipe.id }

        if (existingRecipe != null) continue

        onRefreshAdded(size - 1, newRecipe)
    }
}