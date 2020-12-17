package com.food.core.repository.user

import com.food.core.network.NetworkResponse
import com.food.core.preferences.Pref
import com.food.core.repository.BaseRepository
import com.food.recipe.common.database.AppDatabase
import com.food.recipe.common.database.model.User
import kotlinx.coroutines.*
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by mikekojansow on 16/08/20.
 * Senior Android Developer
 */
class UserRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val database: AppDatabase?
) : BaseRepository(retrofit) {
    private val userService = retrofit.create(UserService::class.java)

    suspend fun getUser(): User? = withContext(Dispatchers.IO) {
        val user = async { fetchUserData() }

        val userFromDatabase = getUserFromDatabase()

        if (userFromDatabase == null) {
            user.await()

            user.getCompleted()
        } else {
            userFromDatabase
        }
    }

    suspend fun updateUser(name: String, biography: String, onFailed: () -> Unit, onSuccess: () -> Unit) = withContext(Dispatchers.IO){
        val user = getUser()

        val updatingUserResponse = userService.updateUserProfile(user?.id ?: "", name, biography)

        when (updatingUserResponse) {
            is NetworkResponse.Success -> {
                updateLocalUser(name, biography)

                onSuccess()
            }
            else -> onFailed()
        }
    }

    suspend fun logoutUser() {
        val user = getUser()

        println("Removing User..")
        user?.let { database?.userDao()?.deleteUser(user) }

        Pref().clearData()
    }

    suspend fun updatePushIdUser(pushId: String) {
        val updatePushId = userService.updatePushNotificationId(pushId)

        when (updatePushId) {
            is NetworkResponse.Success -> { }
            else -> {}
        }
    }

    private fun updateLocalUser(name: String, biography: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = getUserFromDatabase()

            val names = name.split(" ")

            var firstname = ""
            var lastname = ""

            for ((index, currentName) in names.withIndex()) {
                if (index == 0) firstname = currentName
                else lastname += "$currentName "
            }

            user?.apply {
                this.firstName = firstname
                this.lastName = lastname
                this.bio = biography
            }?.let { database?.userDao()?.insertUser(user) }
        }
    }

    private suspend fun fetchUserData() = withContext(Dispatchers.IO) {
        val userResponse = userService.getUserProfile()

        when (userResponse) {
            is NetworkResponse.Success -> {
                userResponse.body.data?.let { user ->
                    database?.userDao()?.insertUser(user)
                }
            }
        }

        null
    }

    private suspend fun getUserFromDatabase(): User? = withContext(Dispatchers.IO) {
        database?.userDao()?.getFirstUser()
    }

}