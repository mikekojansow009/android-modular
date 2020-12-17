package com.food.feature.recipe.detail

import com.food.core.repository.recipe.RecipeRepository
import com.food.recipe.common.database.model.Recipe
import kotlinx.coroutines.*
import kotlin.math.max

/**
 * Created by mikekojansow on 30/08/20.
 * Senior Android Developer
 */
class RecipeDetailPresenter(
    private val activity: RecipeDetailActivity,
    private val recipeRepository: RecipeRepository
) {

    private var currentRecipe: Recipe? = null

    private val delayAfterLoadingDismiss = 600L

    private var currentScrollY = 0

    fun initiateWith(recipe: Recipe?, recipeId: String?) {
        if (recipe == null && recipeId == null) return

        var newRecipeId = recipeId
        if (recipe != null) {
            currentRecipe = recipe

            activity.setUpRecipeView(currentRecipe!!)

            newRecipeId = recipe.id
        } else {
            newRecipeId = recipeId
        }


        requestRecipeDetail(newRecipeId!!)
    }

    fun markFavorite(recipeId: String, favoriteResult: (Boolean, Int) -> Unit) {
        GlobalScope.async {
            recipeRepository.markAsFavorite(recipeId, onFailed = {}, onSuccess = { favoriteResponse ->
                favoriteResult(favoriteResponse.isFavorite, favoriteResponse.likes)
            })
        }
    }

    fun calculateScrollContent(scrollY: Int) {
        currentScrollY += scrollY

        activity.animateParallaxRecipe(currentScrollY)

        val differentScroll = if (activity.getRecipeImageHeight() > 0) currentScrollY.toFloat() / (activity.getRecipeImageHeight() - activity.getToolbarHeight()).toFloat() else 0f

        val alpha = max(differentScroll, 0f)

        activity.setToolbarAlpha(alpha)

        val colorIcon = if (alpha > 0.5f) R.color.black else R.color.white
        activity.setColorIconBack(colorIcon)
    }

    private fun requestRecipeDetail(recipeId: String) {
        GlobalScope.async {
            val recipeDetail = recipeRepository.fetchRecipeDetail(recipeId)

            delay(delayAfterLoadingDismiss)

            withContext(Dispatchers.Main) {
                activity.hideLoading()
                if (recipeDetail != null) {
                    println("Recipe Detail : $recipeDetail")
                    activity.setUpRecipeView(recipeDetail)

                    // For now we only have interstitial ad
                    if (recipeDetail.recipeAd != null) {
                        println("Showing interstitial ad")
                        activity.showInterstitialAd(recipeDetail.recipeAd!!.adsId)
                    }
                }

                println("Recipe Detail Null : $recipeDetail")
            }
        }
    }

}