package com.food.core.repository.body

import com.google.gson.annotations.SerializedName

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
data class MarkFavoriteParams(@SerializedName("recipe_id") val recipeId: String)