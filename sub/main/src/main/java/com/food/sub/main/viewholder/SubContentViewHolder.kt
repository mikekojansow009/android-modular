package com.food.sub.main.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.food.recipe.common.database.model.Recipe
import com.food.view.recipe.RecipeView

/**
 * Created by mikekojansow on 11/08/20.
 * Senior Android Developer
 */
class SubContentViewHolder(private val recipeView: RecipeView) :
    RecyclerView.ViewHolder(recipeView) {

    fun setContentRecipe(recipe: com.food.recipe.common.database.model.Recipe) {
        recipeView.setRecipe(recipe)
    }

}