package com.food.core.repository.preference

import com.food.core.network.NetworkResponse
import com.food.core.preferences.Pref
import com.food.core.repository.BaseRepository
import com.food.recipe.common.database.AppDatabase
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by mikekojansow on 08/10/20.
 * Senior Android Developer
 */
class UserPreferencesRepository@Inject constructor(
    private val retrofit: Retrofit,
    private val database: AppDatabase?
) : BaseRepository(retrofit) {

    private val service = retrofit.create(UserPreferencesService::class.java)

    suspend fun getUserPreferences() {
        val userPreferencesResponse = service.loadPreferences()

        when (userPreferencesResponse) {
            is NetworkResponse.Success -> {
                Pref().userPref = userPreferencesResponse.body.data
            }
        }
    }
}