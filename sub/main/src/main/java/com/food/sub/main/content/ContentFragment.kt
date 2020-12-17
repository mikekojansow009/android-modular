package com.food.sub.main.content

import android.os.Bundle
import android.view.View
import com.food.core.base.fragment.BaseFragment
import com.food.core.dependecy.di.module.ContextModule
import com.food.core.utility.Actions
import com.food.feature.recipe.detail.RecipeDetailDialog
import com.food.recipe.common.database.event.FavoriteRecipeChangeEvent
import com.food.recipe.common.database.model.Recipe
import com.food.sub.main.R
import kotlinx.android.synthetic.main.fragment_content.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Created by mikekojansow on 11/08/20.
 * Senior Android Developer
 */
class ContentFragment: BaseFragment(R.layout.fragment_content) {

    companion object {
        const val TAG_RECIPE = "recipe"
    }

    @Inject lateinit var presenter: ContentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerContentComponent.builder().contextModule(ContextModule(context!!)).contentModule(ContentModule(this)).build().inject(this)

        setSubViews()

        presenter.validateRecipe(arguments?.getParcelable(TAG_RECIPE))
    }

    private fun setSubViews() {
        recipe_view.setOnFavoriteClicked { recipeId ->
            recipeId?.let { presenter.markFavorite(recipeId) }
        }

        recipe_view.setOnRecipeClicked { recipe ->
            showRecipeDetailDialog(recipe)
        }
    }

    private fun showRecipeDetailDialog(recipe: Recipe) {
        if (activity == null) return

        Actions.navigateToRecipeDetail(activity!!, recipe)
    }

    fun setRecipeView(recipe: Recipe) {
        recipe_view.setRecipe(recipe)
    }

    fun changeTotalLikeRecipe(like: Int) {
        recipe_view.changeTotalLike(like)
    }

    fun playAnimationDislikeRecipe() {
        recipe_view.playAnimationDislike()
    }

    fun playAnimationLikeRecipe() {
        recipe_view.playAnimationLike()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRecipeFavoriteChange(event: FavoriteRecipeChangeEvent) {
        presenter.validateRecipe(event.recipe)
    }
}