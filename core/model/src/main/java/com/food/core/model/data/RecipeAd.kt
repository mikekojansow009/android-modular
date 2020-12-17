package com.food.core.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by mikekojansow on 10/10/20.
 * Senior Android Developer
 */
@Parcelize
data class RecipeAd(@SerializedName("type") val typeString: String, @SerializedName("ads_id") val adsId: String): Parcelable {
    val adType: RecipeAdType = RecipeAdType.valueOf(typeString)
}

enum class RecipeAdType {
    BANNER,
    INTERSTITIAL,
    NATIVE
}