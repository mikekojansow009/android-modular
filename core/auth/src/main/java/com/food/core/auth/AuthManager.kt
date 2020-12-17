package com.food.core.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.food.core.auth.impl.FacebookImpl
import com.food.core.auth.impl.GoogleImpl
import com.food.core.preferences.Pref
import com.food.core.utility.Log

/**
 * Created by mikekojansow on 01/08/20.
 * Senior Android Developer
 */
class AuthManager(private val activity: AppCompatActivity) {

    companion object {
        private var currentSocialMediaType: SocialMediaType? = null
        private var currentSocialMediaImpl: SocialMediaInterface? = null
    }

    fun doLogin(
        socialMediaType: SocialMediaType,
        onSuccess: (String) -> Unit,
        onFailed: (Exception?) -> Unit
    ) {
        currentSocialMediaType = socialMediaType

        getSocialMediaImpl()?.doLogin(activity, onSuccess, onFailed)
    }

    fun doLogout(onSuccess: () -> Unit, onFailed: (Exception?) -> Unit) {
        Pref().loginType?.let { loginType -> currentSocialMediaType = SocialMediaType.valueOf(loginType) }

        getSocialMediaImpl()?.doLogout(onSuccess, onFailed)
    }

    fun onActivityResult(requstCode: Int, resultCode: Int, data: Intent?) {
        getSocialMediaImpl()?.onActivityResult(requstCode, resultCode, data)
    }

    private fun getSocialMediaImpl(): SocialMediaInterface? {
        return when (currentSocialMediaType) {
            SocialMediaType.FACEBOOK -> {
                if (currentSocialMediaImpl !is FacebookImpl) currentSocialMediaImpl = FacebookImpl()

                currentSocialMediaImpl
            }
            SocialMediaType.GOOGLE -> {
                if (currentSocialMediaImpl !is GoogleImpl) currentSocialMediaImpl = GoogleImpl()

                currentSocialMediaImpl
            }
            else -> null
        }
    }
}