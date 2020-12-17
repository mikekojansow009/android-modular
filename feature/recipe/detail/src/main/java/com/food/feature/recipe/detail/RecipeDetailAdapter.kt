package com.food.feature.recipe.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.core.model.extension.getTimeInMinutes
import com.food.feature.recipe.detail.viewholder.RecipeContentViewHolder
import com.food.feature.recipe.detail.viewholder.RecipeInstructionsViewHolder
import com.food.feature.recipe.detail.viewholder.RecipeTopViewHolder
import com.food.recipe.common.database.event.FavoriteRecipeChangeEvent
import com.food.recipe.common.database.model.Recipe
import org.greenrobot.eventbus.EventBus

/**
 * Created by mikekojansow on 31/08/20.
 * Senior Android Developer
 */
class RecipeDetailAdapter(
    private var recipe: Recipe,
    private var onRequestFavorite: (String, (Boolean, Int) -> Unit) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val RECIPE_TOP = 0
        private const val RECIPE_CONTENT = 1
        private const val RECIPE_INSTRUCTIONS = 2
    }

    private var showLoading = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RECIPE_TOP -> RecipeTopViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_recipe_top, parent, false))
            RECIPE_CONTENT -> RecipeContentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_recipe_content, parent, false))
            else -> RecipeInstructionsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_recipe_instructions, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecipeTopViewHolder -> {
                holder.setRecipe(
                    recipe.id,
                    recipe.name,
                    recipe.imageUrl,
                    recipe.preparationTime,
                    recipe.cuisine,
                    recipe.description,
                    recipe.likes,
                    recipe.isFavorite,
                    recipe.categories,
                    onFavoriteClicked = {
                        onRequestFavorite(it) { isFavorite, totalLike ->

                            holder.apply {
                                if (isFavorite) playAnimationLike()
                                else playAnimationDislike()

                                changeTotalLike(totalLike)
                            }

                            recipe.isFavorite = isFavorite
                            recipe.likes = totalLike
                        }
                    })
            }
            is RecipeContentViewHolder -> {
                holder.setContentRecipe(recipe, showLoading)
            }
            is RecipeInstructionsViewHolder -> {
                holder.setInstructions(recipe.instructions)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun updateRecipe(recipe: Recipe) {
        this.recipe = recipe
    }

    fun dismissLoading() {
        showLoading = false

        notifyDataSetChanged()
    }
}