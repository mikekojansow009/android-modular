package com.food.core.repository.preference

import com.food.core.model.data.UserPreference
import com.food.core.network.NetworkResponse
import com.food.core.network.Response
import retrofit2.http.GET

/**
 * Created by mikekojansow on 08/10/20.
 * Senior Android Developer
 */
interface UserPreferencesService {
    @GET("user/preferences")
    suspend fun loadPreferences(): NetworkResponse<Response.Success<UserPreference>, Response.Error<Nothing>>
}