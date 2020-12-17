package com.food.core.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by mikekojansow on 02/08/20.
 * Senior Android Developer
 */
@Parcelize
data class Token(
    @SerializedName("access_token") val accessToken: TokenDetail,
    @SerializedName("refresh_token") val refreshToken: TokenDetail
) : Parcelable

@Parcelize
data class TokenDetail(
    val token: String,
    @SerializedName("expires_in") val expiresIn: Int?
): Parcelable