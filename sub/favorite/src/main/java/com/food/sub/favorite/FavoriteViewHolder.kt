package com.food.sub.favorite

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.food.recipe.common.database.model.Recipe
import com.food.view.recipe.RecipePotraitView
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
class FavoriteViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun setData(recipe: Recipe, onRecipeClicked: (Recipe) -> Unit) {
        (containerView as RecipePotraitView).apply {
            setRecipe(recipe)

            setOnRecipeClicked {
                onRecipeClicked(it)
            }
        }
    }

}