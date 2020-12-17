package com.food.feature.recipe.detail.viewholder

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.food.core.model.data.DefaultContent
import com.food.feature.recipe.detail.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_recipe_instructions.*
import kotlinx.android.synthetic.main.view_ingredient_content.view.*
import kotlinx.android.synthetic.main.view_top_content.view.*

/**
 * Created by mikekojansow on 18/09/20.
 * Senior Android Developer
 */
class RecipeInstructionsViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun setInstructions(instructions: List<DefaultContent>?) {
        if (instructions == null) return

        layout_instructions.removeAllViews()

        instructions.forEach { instruction ->
            val view = LayoutInflater.from(containerView.context).inflate(R.layout.view_top_content, null)

            view.tv_title.text = instruction.title

            instruction.contents.forEach { content ->
                val textContentView = LayoutInflater.from(containerView.context).inflate(R.layout.view_ingredient_content, null)

                textContentView.tv_content.text = content

                view.layout_content.addView(textContentView)
            }

            layout_instructions.addView(view)
        }
    }

}