package com.food.sub.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.food.recipe.common.database.model.Recipe
import com.food.view.recipe.RecipeView
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by mikekojansow on 30/09/20.
 * Senior Android Developer
 */
class SearchViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindRecipe(recipe: Recipe, onMarkAsFavoriteClicked: (Recipe) -> Unit, onRecipeClicked: (Recipe) -> Unit) {
        (containerView as RecipeView).apply {
            setRecipe(recipe)
            setOnRecipeClicked {
                onRecipeClicked(it)
            }

            setOnFavoriteClicked {
                onMarkAsFavoriteClicked(recipe)
            }
        }
    }

    fun animateLike() {
        (containerView as RecipeView).playAnimationLike()
    }

    fun animateDislike() {
        (containerView as RecipeView).playAnimationDislike()
    }

}