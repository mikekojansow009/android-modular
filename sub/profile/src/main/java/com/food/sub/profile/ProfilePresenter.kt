package com.food.sub.profile

import com.food.core.preferences.Pref
import com.food.core.repository.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by mikekojansow on 12/08/20.
 * Senior Android Developer
 */
class ProfilePresenter @Inject constructor(
    private val fragment: ProfileFragment,
    private val userRepository: UserRepository
) {

    fun getUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userRepository.getUser()

            withContext(Dispatchers.Main) {
                user?.let {
                    fragment.setProfile(it.firstName, it.imageUrl, it.getFollowerUser(), it.getFollowingUser())
                }
            }
        }
    }
}