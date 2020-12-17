package com.food.core.repository

import com.food.core.exception.UnknownTokenException
import com.food.core.model.extension.getCurrentTime
import com.food.core.network.NetworkResponse
import com.food.core.preferences.Pref
import com.food.core.repository.auth.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by mikekojansow on 20/08/20.
 * Senior Android Developer
 */
open class BaseRepository @Inject constructor(retrofit: Retrofit) {
    private var service = retrofit.create(AuthService::class.java)

    suspend fun proceedNewAccessTokenIfNeeded(onFinished: () -> Unit) = runBlocking {
        if (isAccessTokenExpired()) {
            getNewAccessToken(onFinished)
        } else { onFinished() }
    }

    private fun isAccessTokenExpired(): Boolean {
        val token = Pref().token

        return token?.accessToken?.expiresIn?.let { expires ->
            expires < getCurrentTime()
        } ?: true
    }

    private suspend fun getNewAccessToken(onFinished: () -> Unit) = withContext(Dispatchers.IO) {
        val token = Pref().token

        token?.let {
            val accessTokenResponse = service.getNewAccessToken(it.refreshToken.token)

            when (accessTokenResponse) {
                is NetworkResponse.Success -> {
                    val newToken = accessTokenResponse.body.data

                    Pref().token = newToken

                    onFinished()
                }
            }
        } ?: throw UnknownTokenException()
    }

}