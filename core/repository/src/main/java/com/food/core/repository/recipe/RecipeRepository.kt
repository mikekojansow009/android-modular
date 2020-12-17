package com.food.core.repository.recipe

import com.food.core.model.data.MarkFavoriteResponse
import com.food.core.network.NetworkResponse
import com.food.core.repository.BaseRepository
import com.food.core.repository.body.MarkFavoriteParams
import com.food.recipe.common.database.AppDatabase
import com.food.recipe.common.database.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
class RecipeRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val database: AppDatabase?
) : BaseRepository(retrofit) {

    private val service = retrofit.create(RecipeService::class.java)

    suspend fun getFavoriteRecipes(page: Int): ArrayList<Recipe> {
        val recipes = fetchFavoriteRecipesFromServer(page)

        // ToDo: get all favorite recipes from local database

        return recipes
    }

    suspend fun fetchRecipeDetail(recipeId: String): Recipe? {
        val recipe = service.recipeDetail(recipeId)

        return when (recipe) {
            is NetworkResponse.Success -> recipe.body.data
            else -> null
        }
    }

    suspend fun fetchRecipeByCategory(page: Int, categoryId: String): ArrayList<Recipe> {
        val recipes = service.recipesByCategory(page, categoryId)

        return when (recipes) {
            is NetworkResponse.Success -> recipes.body.data ?: arrayListOf()
            else -> arrayListOf()
        }
    }

    suspend fun markAsFavorite(recipeId: String, onSuccess: (MarkFavoriteResponse) -> Unit, onFailed: () -> Unit) {
        val params = MarkFavoriteParams(recipeId)

        val markFavoriteResponse = service.markAsFavorite(params)

        when (markFavoriteResponse) {
            is NetworkResponse.Success -> {
                val favoriteDataResponse = markFavoriteResponse.body.data

                withContext(Dispatchers.Main) {
                    favoriteDataResponse?.let(onSuccess)
                }
            }
            else -> { withContext(Dispatchers.Main) { onFailed() }}
        }
    }

    suspend fun searchRecipe(page: Int, name: String): ArrayList<Recipe> {
        val recipes = service.searchRecipes(page, name)

        return when (recipes) {
            is NetworkResponse.Success -> recipes.body.data ?: arrayListOf()
            else -> arrayListOf()
        }
    }

    // ToDo: save all favorite recipes to local database
    private suspend fun fetchFavoriteRecipesFromServer(page: Int): ArrayList<Recipe> = withContext(Dispatchers.IO){
        val recipes = service.recipeFavorites(page)

        when (recipes) {
            is NetworkResponse.Success -> {
                val recipeDatas = recipes.body.data

                recipeDatas ?: arrayListOf()
            }
            else -> arrayListOf()
        }
    }

}