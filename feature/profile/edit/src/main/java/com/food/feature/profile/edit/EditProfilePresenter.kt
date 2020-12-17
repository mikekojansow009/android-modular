package com.food.feature.profile.edit

import com.food.core.repository.user.UserRepository
import com.food.core.utility.BaseInfo
import com.food.recipe.common.database.model.User
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Created by mikekojansow on 22/08/20.
 * Senior Android Developer
 */
class EditProfilePresenter @Inject constructor(
    private val activity: EditProfileActivity,
    private val userRepository: UserRepository
) {

    private var currentUser: User? = null

    fun setupData() {
        loadProfileDetail()
        activity.setVersionName("v${BaseInfo.versionName}")
    }

    fun requestUpdateUser(name: String, biography: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.updateUser(name, biography, onFailed = ::onFailed, onSuccess = ::onSucccess)
        }
    }

    fun validateChangesUserData(name: String, biography: String) {
        if (currentUser == null || biography.isEmpty()) return

        if (currentUser!!.getFullName() != name || currentUser!!.bio != biography) {
            activity.showSaveButton()

            return
        }

        if (activity.getHeightCurrentSaveButton() > 0) activity.hideSaveButton()
    }

    fun logoutUser() {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.logoutUser()

            withContext(Dispatchers.Main) {
                activity.navigateToLogin()
            }
        }
    }

    private fun loadProfileDetail() {
        CoroutineScope(Dispatchers.IO).async {
            val user = userRepository.getUser()

            user?.let {
                currentUser = it
                withContext(Dispatchers.Main) {
                    activity.setUser(user)
                }
            }
        }
    }

    private fun onFailed() {
        CoroutineScope(Dispatchers.Main).launch {
            activity.showUpdateFailed()
            activity.hideLoadingButton()
        }
    }

    private fun onSucccess() {
        CoroutineScope(Dispatchers.Main).launch {
            activity.showUpdateSuccess()
            activity.hideLoadingButton()
            activity.hideSaveButton()
        }
    }
}
