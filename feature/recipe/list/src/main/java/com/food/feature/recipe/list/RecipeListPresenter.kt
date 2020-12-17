package com.food.feature.recipe.list

import com.food.core.repository.recipe.RecipeRepository
import com.food.recipe.common.database.event.FavoriteRecipeChangeEvent
import com.food.recipe.common.database.model.Recipe
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Created by mikekojansow on 05/09/20.
 * Senior Android Developer
 */
class RecipeListPresenter @Inject constructor(
    private val activity: RecipeListActivity,
    private val recipeRepository: RecipeRepository
) {
    var recipes = ArrayList<Recipe>()
        private set

    private var allRecipeIds = ArrayList<String>()
    private var categoryId = ""

    fun validateRecipeCategory(currentPage: Int, categoryIdExtra: String?, title: String?) {
        if (categoryIdExtra == null || title == null) return

        activity.setTitleToolbar(title)

        categoryId = categoryIdExtra

        loadAllRecipesByCategory(currentPage)
    }

    fun loadAllRecipesByCategory(page: Int) {
        CoroutineScope(Dispatchers.IO).async {
            val allRecipes = recipeRepository.fetchRecipeByCategory(page, categoryId)

            removeDuplicateIdRecipeIfNeeded(allRecipes)

            withContext(Dispatchers.Main) {
                delay(500L)

                activity.hideRefreshLoading()
            }
        }
    }

    fun markRecipe(recipeId: String, position: Int) {
        CoroutineScope(Dispatchers.IO).async {
            recipeRepository.markAsFavorite(recipeId, onSuccess = {
                if (it.isFavorite) activity.animateLikeRecipe(position)
                else activity.animateDislikeRecipe(position)

                for (recipeChanges in recipes) {
                    if (recipeChanges.id == recipeId) {
                        recipeChanges.likes = it.likes
                        recipeChanges.isFavorite = it.isFavorite

                        EventBus.getDefault().post(FavoriteRecipeChangeEvent(recipeChanges))

                        GlobalScope.async {
                            withContext(Dispatchers.Main) {
                                delay(700L)

                                activity.refreshItemChanged()
                            }
                        }
                    }
                }
            }, onFailed = {})
        }
    }

    private suspend fun removeDuplicateIdRecipeIfNeeded(newRecipes: List<Recipe>) {
        val newRecipeIds = ArrayList<String>()
        newRecipes.forEach { recipe ->
            newRecipeIds.add(recipe.id)
        }

        if (recipes.isEmpty()) {
            withContext(Dispatchers.Main) {
                newRecipes.forEach { recipe ->
                    recipes.add(recipe)

                    val lastPosition = recipes.size - 1
                    activity.refreshItemAdded(lastPosition)
                }
            }

            allRecipeIds.addAll(newRecipeIds)
            return
        }

        for (newRecipeId in newRecipeIds) {
            val sameRecipeId = allRecipeIds.find { it == newRecipeId }

            if (sameRecipeId != null) continue

            newRecipes.find { it.id == newRecipeId }?.let { newRecipe ->
                recipes.add(newRecipe)

                withContext(Dispatchers.Main) {
                    activity.refreshItemAdded(recipes.size - 1)
                }
            }

            allRecipeIds.add(newRecipeId)
        }
    }
}