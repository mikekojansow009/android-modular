package com.food.feature.recipe.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.food.core.utility.Actions
import com.food.feature.recipe.detail.RecipeDetailDialog
import com.food.recipe.common.database.model.Recipe
import com.food.view.recipe.RecipeView
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by mikekojansow on 08/09/20.
 * Senior Android Developer
 */
class RecipeListViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun setRecipe(recipe: Recipe, onRecipeClicked: (Recipe) -> Unit, onFavoriteClicked: (Recipe) -> Unit) {
        (containerView as RecipeView).apply {
            setRecipe(recipe)
            setOnRecipeClicked {
                onRecipeClicked(it)
            }

            setOnFavoriteClicked {
                onFavoriteClicked(recipe)
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