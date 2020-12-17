package com.food.feature.login

import com.food.core.auth.SocialMediaType
import com.food.core.model.data.Token
import com.food.core.network.NetworkResponse
import com.food.core.network.Response
import com.food.core.utility.Log
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Inject

/**
 * Created by mikekojansow on 01/08/20.
 * Senior Android Developer
 */
class LoginRepository @Inject constructor(private val retrofit: Retrofit) {

    private val service = retrofit.create(LoginService::class.java)

    fun loginAnonymous(
        onSuccess: (Token) -> Unit,
        onFailed: () -> Unit
    ) {
        GlobalScope.async(context = Dispatchers.IO) {
            val responseLogin = service.login()

            when (responseLogin) {
                is NetworkResponse.Success -> {
                    val token = responseLogin.body.data

                    token?.let(onSuccess) ?: onFailed()
                }
                is NetworkResponse.ApiError -> onFailed()
                is NetworkResponse.UnknownError -> {
                    Log.printLog("Unknown", "Unknown dialog_error")
                    onFailed()
                }
            }
        }
    }

    fun socialMediaAuthenticate(
        accessToken: String,
        type: SocialMediaType,
        onSuccess: (Token) -> Unit,
        onFailed: () -> Unit
    ) {
        GlobalScope.async {
            val authRequest = AuthRequest(accessToken, type.name)
            val authentication = service.authenticate(authRequest)

            when (authentication) {
                is NetworkResponse.Success -> {
                    val token = authentication.body.data

                    token?.let(onSuccess) ?: onFailed()
                }
                is NetworkResponse.ApiError -> onFailed()
                is NetworkResponse.UnknownError -> {
                    Log.printLog("Unknown Error", "Error")
                    onFailed()
                }
            }
        }
    }

    private interface LoginService {
        @GET("anonymous")
        suspend fun login(): NetworkResponse<Response.Success<Token>, Response.Error<Nothing>>

        @POST("authenticate")
        suspend fun authenticate(@Body authRequest: AuthRequest): NetworkResponse<Response.Success<Token>, Response.Error<Nothing>>
    }

    private data class AuthRequest(
        @SerializedName("access_token") val accessToken: String,
        val type: String
    )

}