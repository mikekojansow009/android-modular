package com.food.core.repository.auth

import com.food.core.model.data.Token
import com.food.core.network.NetworkResponse
import com.food.core.network.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by mikekojansow on 20/08/20.
 * Senior Android Developer
 */
interface AuthService {

    @GET("auth/refresh")
    fun getNewAccessToken(@Query("refresh_token") refreshToken: String): NetworkResponse<Response.Success<Token>, Response.Error<Nothing>>

}