package com.food.sub.main.model

import com.food.recipe.common.database.model.Recipe

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
data class HomeRecipe(
    val id: String,
    val name: String,
    var recipes: ArrayList<Recipe>,
    val type: String
)
