package com.food.feature.recipe.detail.viewholder

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.food.core.model.data.DefaultContent
import com.food.feature.recipe.detail.R
import com.food.recipe.common.database.model.Recipe
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_recipe_content.*
import kotlinx.android.synthetic.main.view_ingredient_content.view.*
import kotlinx.android.synthetic.main.view_top_content.view.*

/**
 * Created by mikekojansow on 31/08/20.
 * Senior Android Developer
 */
class RecipeContentViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun setContentRecipe(recipe: Recipe, showLoading: Boolean) {
        if (showLoading) showLoading() else hideLoading()

        recipe.ingredients?.let {
            println("Ingredients : $it")
            setIngredients(it)
        }
    }

    private fun setIngredients(contents: List<DefaultContent>) {
        layout_ingredients.removeAllViews()

        contents.forEach { ingredient ->
            val view = LayoutInflater.from(containerView.context).inflate(R.layout.view_top_content, null)

            view.tv_title.text = ingredient.title

            ingredient.contents.forEach { content ->
                val textContentView = LayoutInflater.from(containerView.context).inflate(R.layout.view_ingredient_content, null)

                textContentView.tv_content.text = content

                view.layout_content.addView(textContentView)
            }

            layout_ingredients.addView(view)
        }
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
    }

}