package com.food.feature.recipe.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.recipe.common.database.model.Recipe

/**
 * Created by mikekojansow on 08/09/20.
 * Senior Android Developer
 */
class RecipeListAdapter(
    private val recipes: ArrayList<Recipe>,
    private var onRecipeClicked: (Recipe) -> Unit,
    private var onFavoriteClicked: (Recipe, Int) -> Unit
): RecyclerView.Adapter<RecipeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        return RecipeListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_view_holder, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        holder.setRecipe(recipes[position], onRecipeClicked, onFavoriteClicked = { recipe ->
            onFavoriteClicked(recipe, position)
        })
    }
}