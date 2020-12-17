package com.food.core.network.intercept

import com.food.core.broadcast.event.TokenExpiredEvent
import com.food.core.model.data.Token
import com.food.core.model.extension.getCurrentTime
import com.food.core.network.NetworkInfo
import com.food.core.preferences.Pref
import com.food.core.utility.BaseInfo
import com.food.core.utility.BuildConfig
import com.google.gson.Gson
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * Created by mikekojansow on 20/08/20.
 * Senior Android Developer
 */
class AuthIntercept : Interceptor {

    private val refreshTokenUrl = "${BaseInfo.baseUrl}authentication/refresh"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        synchronized(true) {
            fetchNewAccessTokenIfNeeded(originalRequest, chain)
        }

        val requestBuilder = if (BuildConfig.DEBUG) originalRequest.newBuilder().method(
            originalRequest.method(),
            originalRequest.body()
        ) else originalRequest.newBuilder()

        for ((key, value) in NetworkInfo.getHeaders()) {
            requestBuilder.header(key, value)
        }

        return chain.proceed(requestBuilder.build())
    }

    private fun isAccessTokenExpired(): Boolean {
        val token = Pref().token

        return token?.accessToken?.expiresIn?.let { it < getCurrentTime() } ?: true
    }

    private fun fetchNewAccessTokenIfNeeded(request: Request, chain: Interceptor.Chain) {
        if (!isAccessTokenExpired()) return

        val token = Pref().token ?: return

        val jsonObj = JSONObject()
        jsonObj.put("refresh_token", token.refreshToken.token)

        val refreshToken = request.newBuilder().get().url(refreshTokenUrl)
            .addHeader("Authorization", "Bearer ${token.accessToken.token}").post(RequestBody.create(
                MediaType.parse("application/json"), jsonObj.toString())).build()

        val response = chain.proceed(refreshToken)

        if (response.isSuccessful) {
            val responseBody = response.body()?.string()

            val jsonResponse = JSONObject(responseBody)

            if (jsonResponse.has("data")) {
                val tokenData = Gson().fromJson(jsonResponse.getString("data"), Token::class.java)

                Pref().token = tokenData
            }
        } else {
            EventBus.getDefault().post(TokenExpiredEvent())
        }

        response.close()
    }
}