package com.food.sub.favorite

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.recipe.common.database.model.Recipe
import com.food.view.recipe.RecipePotraitView

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
class FavoriteAdapter(private var recipes: ArrayList<Recipe>, private var onRecipeClicked: (Recipe) -> Unit) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(RecipePotraitView(parent.context))
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.setData(recipes[position], onRecipeClicked)
    }
}