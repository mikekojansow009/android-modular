package com.food.core.repository.recipe

import com.food.core.model.data.MarkFavoriteResponse
import com.food.core.network.NetworkResponse
import com.food.core.network.Response
import com.food.core.repository.body.MarkFavoriteParams
import com.food.recipe.common.database.model.Recipe
import retrofit2.http.*

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
interface RecipeService {

    @GET("user/food/favorite")
    suspend fun recipeFavorites(@Query("page") page: Int): NetworkResponse<Response.Success<ArrayList<Recipe>>, Response.Error<Nothing>>

    @POST("user/food/favorite")
    suspend fun markAsFavorite(@Body params: MarkFavoriteParams): NetworkResponse<Response.Success<MarkFavoriteResponse>, Response.Error<Nothing>>

    @GET("food/recipes/{recipeId}")
    suspend fun recipeDetail(@Path("recipeId") recipeId: String): NetworkResponse<Response.Success<Recipe>, Response.Error<Nothing>>

    @GET("food/recipes")
    suspend fun recipesByCategory(@Query("page") page: Int, @Query("category") categoryId: String): NetworkResponse<Response.Success<ArrayList<Recipe>>, Response.Error<Nothing>>

    @GET("food/search")
    suspend fun searchRecipes(@Query("page") page: Int, @Query("name") name: String): NetworkResponse<Response.Success<ArrayList<Recipe>>, Response.Error<Nothing>>
}