package com.food.recipe.common.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Created by mikekojansow on 02/08/20.
 * Senior Android Developer
 */
@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val email: String?,
    @SerializedName("first_name") var firstName: String,
    @SerializedName("last_name") var lastName: String?,
    @SerializedName("image_url") val imageUrl: String?,
    val following: Int?,
    val follower: Int?,
    var bio: String?
): Parcelable {

    @Ignore
    fun getFollowingUser(): Int {
        return following ?: 0
    }

    @Ignore
    fun getFollowerUser():Int {
        return follower ?: 0
    }

    @Ignore
    fun getFullName(): String {
        return "$firstName ${lastName ?: ""}"
    }

}