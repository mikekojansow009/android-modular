package com.food.core.repository.user

import com.food.recipe.common.database.model.User
import com.food.core.network.NetworkResponse
import com.food.core.network.Response
import com.google.gson.annotations.SerializedName
import retrofit2.http.*

/**
 * Created by mikekojansow on 16/08/20.
 * Senior Android Developer
 */
interface UserService {

    @GET("user/profile")
    suspend fun getUserProfile(): NetworkResponse<Response.Success<User>, Response.Error<Nothing>>

    @FormUrlEncoded
    @PUT("user/profile/{id}")
    suspend fun updateUserProfile(@Path(value="id", encoded = true) id: String, @Field("name") name: String, @Field("biography") biography: String): NetworkResponse<Response.Success<String>, Response.Error<Nothing>>

    @FormUrlEncoded
    @POST("user/push/notification")
    suspend fun updatePushNotificationId(@Field("push_notification") pushNotificationId: String): NetworkResponse<Response.Success<String>, Response.Error<Nothing>>
}