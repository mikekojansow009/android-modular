package com.food.feature.home

import com.food.core.preferences.Pref
import com.food.core.repository.preference.UserPreferencesRepository
import com.food.core.repository.user.UserRepository
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Created by mikekojansow on 16/08/20.
 * Senior Android Developer
 */
class HomePresenter @Inject constructor(
    private val homeActivity: HomeActivity,
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    fun loadUserProfile() {
        GlobalScope.async(Dispatchers.IO) {
            val user = userRepository.getUser()

            userPreferencesRepository.getUserPreferences()
        }
    }

    fun updatePushId() {
        CoroutineScope(Dispatchers.IO).launch {
            val pushId = Pref().oneSignalId

            if (pushId != null) userRepository.updatePushIdUser(pushId)
        }
    }

}