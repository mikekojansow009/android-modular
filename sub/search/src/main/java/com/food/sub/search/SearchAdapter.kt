package com.food.sub.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.recipe.common.database.model.Recipe

/**
 * Created by mikekojansow on 30/09/20.
 * Senior Android Developer
 */
class SearchAdapter(
    private var recipes: ArrayList<Recipe>,
    private var onMarkAsFavorite: (Recipe) -> Unit,
    private var onRecipeClicked: (Recipe) -> Unit
) : RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.search_view_holder, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindRecipe(recipes[position], onMarkAsFavorite, onRecipeClicked)
    }
}