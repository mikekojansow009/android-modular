package com.food.feature.recipe.detail

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.core.base.activity.AuthActivity
import com.food.core.dependecy.di.module.ContextModule
import com.food.feature.recipe.detail.viewholder.RecipeTopViewHolder
import com.food.recipe.common.database.event.FavoriteRecipeChangeEvent
import com.food.recipe.common.database.model.Recipe
import com.food.thridparty.ads.RecipeAds
import kotlinx.android.synthetic.main.recipe_detail.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Created by mikekojansow on 27/09/20.
 * Senior Android Developer
 */
class RecipeDetailActivity: AuthActivity(R.layout.recipe_detail) {

    companion object {
        const val TAG_RECIPE = "RECIPE"
        const val TAG_RECIPE_ID = "RECIPE_ID"
    }

    @Inject
    lateinit var presenter: RecipeDetailPresenter

    private var adapter: RecipeDetailAdapter? = null

    private var recipe: Recipe? = null
    private var recipeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerRecipeDetailComponent.builder().recipeDetailModule(RecipeDetailModule(this)).contextModule(
            ContextModule(this)
        ).build().inject(this)

        recipe = intent.getParcelableExtra(TAG_RECIPE)
        recipeId = intent.getStringExtra(TAG_RECIPE_ID)
        presenter.initiateWith(recipe = recipe, recipeId = recipeId)

        setupView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        setupRecyclerView()
        setupToolbar()
    }

    private fun setupToolbar() {
        iv_back.setOnClickListener {
            onBackPressed()
        }

        setColorIconBack(R.color.white)

        toolbar.setTitle(recipe?.name ?: "")

        toolbar.alpha = 0f
    }

    private fun setupRecyclerView() {
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    presenter.calculateScrollContent(dy)
                }
            })
        }
    }

    fun animateParallaxRecipe(scrollY: Int) {
        val viewHolder = recyclerview.findViewHolderForAdapterPosition(0)

        if (viewHolder is RecipeTopViewHolder) {
            viewHolder.parallaxRecipeImage(scrollY.toFloat())
        }
    }

    fun getRecipeImageHeight(): Int {
        val viewHolder = recyclerview.findViewHolderForAdapterPosition(0)

        if (viewHolder is RecipeTopViewHolder) return viewHolder.imageRecipeHeight

        return 0
    }

    fun setToolbarAlpha(alpha: Float) {
        toolbar.alpha = alpha
    }

    fun getToolbarHeight(): Int {
        return toolbar.height
    }

    fun setColorIconBack(colorId: Int) {
        iv_back.setColorFilter(ContextCompat.getColor(this, colorId), PorterDuff.Mode.SRC_IN)
    }

    fun setUpRecipeView(recipe: Recipe) {
        if (adapter == null) {
            adapter = RecipeDetailAdapter(recipe, onRequestFavorite = { recipeId, response ->
                presenter.markFavorite(recipeId, favoriteResult = { isFavorite, likes ->
                    response(isFavorite, likes)
                    recipe.isFavorite = isFavorite
                    recipe.likes = likes

                    EventBus.getDefault().post(FavoriteRecipeChangeEvent(recipe))
                })
            })

            recyclerview.adapter = adapter

            return
        }

        adapter?.updateRecipe(recipe)
        adapter?.notifyDataSetChanged()
    }

    fun showInterstitialAd(adsId: String) {
        RecipeAds.initiateAds(this)

        RecipeAds.showInterstitialAd(this, adsId)
    }

    fun hideLoading() {
        adapter?.dismissLoading()
    }

}