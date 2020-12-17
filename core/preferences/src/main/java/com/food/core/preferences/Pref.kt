package com.food.core.preferences

import android.content.Context
import android.content.SharedPreferences
import com.food.core.model.data.Token
import com.food.core.model.data.UserPreference
import com.google.gson.Gson
import java.util.*

/**
 * Created by mikekojansow on 24/07/20.
 * Senior Android Developer
 */
class Pref {

    companion object {
        private const val USER_SEGMENT = "user_segment"
        private const val PRIVATE_SEGMENT = "private_segment"

        private const val TUTORIAL_SHOWN = "tutorial_shown"
        private const val USER_TOKEN = "user_token"
        private const val USER_UUID = "user_uuid"
        private const val USER_LOGIN_TYPE = "user_login_type"
        private const val USER_PREFERENCE = "user_preferences"
        private const val ONE_SIGNAL_USER_ID = "one_signal_user_id"

        private lateinit var userPreferences: SharedPreferences
        private lateinit var privatePreferences: SharedPreferences

        fun initiate(context: Context) {
            if (!::userPreferences.isInitialized) userPreferences = context.getSharedPreferences(
                USER_SEGMENT, Context.MODE_PRIVATE)

            if (!::privatePreferences.isInitialized) privatePreferences = context.getSharedPreferences(
                PRIVATE_SEGMENT, Context.MODE_PRIVATE)
        }
    }

    var tutorialHasShown: Boolean = false
        get() {
            return userPreferences.getBoolean(TUTORIAL_SHOWN, false)
        }
        set(value) {
            field = value

            userPreferences.apply { edit().putBoolean(TUTORIAL_SHOWN, true).apply() }
        }

    var token: Token? = null
        get() {
            val tokenDataString = userPreferences.getString(USER_TOKEN, null)

            tokenDataString?.let { tokenData ->
                return Gson().fromJson(tokenData, Token::class.java)
            }

            return null
        }
        set(value) {
            field = value

            userPreferences.apply { edit().putString(USER_TOKEN, Gson().toJson(value)).apply() }
        }

    val uuid: String
        get() {
            var userUuid = privatePreferences.getString(USER_UUID, null)

            if (userUuid == null) {
                userUuid = UUID.randomUUID().toString()

                privatePreferences.apply { edit().putString(USER_UUID, userUuid).apply() }
            }

            return userUuid
        }

    var loginType: String? = null
        get() {
            val currentLoginType = userPreferences.getString(USER_LOGIN_TYPE, null)

            return currentLoginType
        }
        set(value) {
            field = value

            userPreferences.apply { edit().putString(USER_LOGIN_TYPE, value).apply() }
        }

    var userPref: UserPreference? = null
        get() {
             val userPrefString = userPreferences.getString(USER_PREFERENCE, null)

            userPrefString?.let {
                return Gson().fromJson(it, UserPreference::class.java)
            }

            return null
        }
        set(value) {
            field = value

            userPreferences.apply { edit().putString(USER_PREFERENCE, Gson().toJson(value)).apply() }
        }

    var oneSignalId: String? = null
        get() {
            return userPreferences.getString(ONE_SIGNAL_USER_ID, null)
        }
        set(value) {
            field = value

            userPreferences.apply { edit().putString(ONE_SIGNAL_USER_ID, value).apply() }
        }

    fun clearData() {
        userPreferences.apply {
            edit().clear().apply()
        }
    }
}