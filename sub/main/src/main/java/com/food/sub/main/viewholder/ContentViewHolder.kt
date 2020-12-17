package com.food.sub.main.viewholder

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.core.utility.Actions
import com.food.sub.main.R
import com.food.sub.main.model.HomeRecipe
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.content_holder.*

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
class ContentViewHolder(override val containerView: View,
                        private val fm: FragmentManager) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun setContent(homeRecipe: HomeRecipe, position: Int) {
        tv_title.text = homeRecipe.name

        val contentAdapter = ContentAdapter(homeRecipe.recipes, fm)

        view_pager.id = position
        view_pager.adapter = contentAdapter
        view_pager.pageMargin = containerView.resources.getDimensionPixelSize(R.dimen.content_between_margin)
        view_pager.clipToPadding = true

        setOnSeeMoreClick(homeRecipe.id, homeRecipe.name)
    }

    private fun setOnSeeMoreClick(categoryId: String, categoryName: String) {
        tv_see_more.setOnClickListener {
            Actions.navigateToRecipeList(containerView.context, categoryId, categoryName)
        }
    }

}