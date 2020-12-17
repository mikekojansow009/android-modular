package com.food.core.model.data

import com.google.gson.annotations.SerializedName

/**
 * Created by mikekojansow on 24/08/20.
 * Senior Android Developer
 */
data class MarkFavoriteResponse(
    @SerializedName("is_favorite") val isFavorite: Boolean,
    val likes: Int
)