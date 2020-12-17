package com.food.feature.recipe.list

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.core.base.activity.AuthActivity
import com.food.core.dependecy.di.module.ContextModule
import com.food.core.utility.Actions
import com.food.core.utility.EndlessScrollViewListener
import com.food.feature.recipe.detail.RecipeDetailDialog
import com.food.recipe.common.database.event.FavoriteRecipeChangeEvent
import com.food.recipe.common.database.model.Recipe
import kotlinx.android.synthetic.main.recipe_list.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Created by mikekojansow on 05/09/20.
 * Senior Android Developer
 */
class RecipeListActivity: AuthActivity(R.layout.recipe_list) {

    companion object {
        private const val TAG_CATEGORY_ID = "CATEGORY_ID"
        private const val TAG_CATEGORY_NAME = "CATEGORY_NAME"
    }

    @Inject lateinit var presenter: RecipeListPresenter

    private var endlessScroll: EndlessScrollViewListener? = null

    private var currentPage = 1

    private var recipeAdapter: RecipeListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerRecipeListComponent.builder().contextModule(ContextModule(this)).recipeListModule(RecipeListModule(this)).build().injectActivity(this)

        presenter.validateRecipeCategory(currentPage, intent.getStringExtra(TAG_CATEGORY_ID), intent.getStringExtra(TAG_CATEGORY_NAME))

        setupView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        setRecyclerView()
        setSwipeRefresh()
        setToolbar()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setSwipeRefresh() {
        swipe_refresh.setOnRefreshListener {
            presenter.loadAllRecipesByCategory(1)
        }
    }

    private fun setRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        endlessScroll = object: EndlessScrollViewListener(linearLayoutManager, currentPage = currentPage) {
            override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                view: RecyclerView?,
                context: Context
            ) {
                currentPage = page

                presenter.loadAllRecipesByCategory(currentPage)
            }
        }

        recipeAdapter = RecipeListAdapter(presenter.recipes, ::onRecipeClicked, ::onRecipeFavoriteClicked)

        recyclerview.apply {
            layoutManager = linearLayoutManager
            adapter = recipeAdapter
            itemAnimator = RecipeItemAnimator()

            removeOnScrollListener(endlessScroll!!)
            addOnScrollListener(endlessScroll!!)
        }
    }

    private fun onRecipeClicked(recipe: Recipe) {
        Actions.navigateToRecipeDetail(this, recipe)
    }

    private fun onRecipeFavoriteClicked(recipe: Recipe, position: Int) {
        presenter.markRecipe(recipe.id, position)
    }

    fun hideRefreshLoading() {
        swipe_refresh.isRefreshing = false
    }

    fun setTitleToolbar(title: String) {
        toolbar.title = title
    }

    fun refreshItemAdded(position: Int) {
        recipeAdapter?.notifyItemInserted(position)
    }

    fun refreshItemChanged() {
        recipeAdapter?.notifyDataSetChanged()
    }

    fun animateLikeRecipe(position: Int) {
        (recyclerview.findViewHolderForAdapterPosition(position) as RecipeListViewHolder).animateLike()
    }

    fun animateDislikeRecipe(position: Int) {
        (recyclerview.findViewHolderForAdapterPosition(position) as RecipeListViewHolder).animateDislike()
    }
}