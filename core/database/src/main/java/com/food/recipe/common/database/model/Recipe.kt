package com.food.recipe.common.database.model

import android.os.Parcelable
import com.food.core.model.data.DefaultContent
import com.food.core.model.data.RecipeAd
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
@Parcelize
data class Recipe(
    val id: String,
    @SerializedName("title") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("preparation_time") val preparationTime: String,
    var likes: Int,
    val description: String,
    val cuisine: String,
    val categories: List<String>,
    val user: User,
    @SerializedName("is_favorite") var isFavorite: Boolean,
    val ingredients: List<DefaultContent>?,
    val instructions: List<DefaultContent>?,
    @SerializedName("ad") val recipeAd: RecipeAd?
): Parcelable