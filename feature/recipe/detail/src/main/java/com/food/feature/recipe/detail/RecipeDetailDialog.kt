package com.food.feature.recipe.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.core.base.fragment.BaseBottomSheetDialog
import com.food.core.dependecy.di.module.ContextModule
import com.food.feature.recipe.detail.viewholder.RecipeTopViewHolder
import com.food.recipe.common.database.model.Recipe
import kotlinx.android.synthetic.main.recipe_detail.*
import javax.inject.Inject


/**
 * Created by mikekojansow on 30/08/20.
 * Senior Android Developer
 */
open class RecipeDetailDialog(private var recipe: Recipe, private var onCloseDialog: (Recipe) -> Unit): BaseBottomSheetDialog() {

    @Inject lateinit var presenter: RecipeDetailPresenter

    private var adapter: RecipeDetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recipe_detail, container)
    }

    override fun onDestroy() {
        onCloseDialog(recipe)
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        DaggerRecipeDetailComponent.builder().recipeDetailModule(RecipeDetailModule(this)).contextModule(
//            ContextModule(context!!)
//        ).build().inject(this)

        setupView()

        presenter.initiateWith(recipe = recipe, recipeId = "")
    }

    private fun setupView() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    animateParallaxRecipe(dy)
                }
            })
        }
    }

    private fun animateParallaxRecipe(scrollY: Int) {
        val viewHolder = recyclerview.findViewHolderForAdapterPosition(0)
        if (viewHolder is RecipeTopViewHolder) {
            viewHolder.parallaxRecipeImage(scrollY.toFloat())
        }
    }

    fun setUpRecipeView(recipe: Recipe) {
        if (adapter == null) {
            adapter = RecipeDetailAdapter(recipe, onRequestFavorite = { recipeId, response ->
                presenter.markFavorite(recipeId, favoriteResult = { isFavorite, likes ->
                    response(isFavorite, likes)
                    recipe.isFavorite = isFavorite
                    recipe.likes = likes
                })
            })
            recyclerview.adapter = adapter

            return
        }
        adapter?.updateRecipe(recipe)
        adapter?.notifyDataSetChanged()
    }

    fun hideLoading() {
        adapter?.dismissLoading()
    }
}