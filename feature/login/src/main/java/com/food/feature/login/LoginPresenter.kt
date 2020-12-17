package com.food.feature.login

import com.food.core.auth.AuthManager
import com.food.core.auth.SocialMediaType
import com.food.core.exception.SignInCancelException
import com.food.core.model.data.Token
import com.food.core.preferences.Pref
import com.food.core.repository.user.UserRepository
import com.food.core.utility.KeyboardUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by mikekojansow on 01/08/20.
 * Senior Android Developer
 */
class LoginPresenter @Inject constructor(
    private val activity: LoginActivity,
    private val repository: LoginRepository,
    private val userRepository: UserRepository,
    private val authManager: AuthManager
) {

    fun showTokenExpiredIfNeeded(isExpired: Boolean) {
        if (isExpired) {
            Pref().clearData()
            activity.showDialogExpired()
        }
    }

    fun addSpaceIfHasNavigationButton() {
        val heightNavigation = KeyboardUtils.getNavigationButtonsHeight(activity)

        if (heightNavigation > 0) {
            activity.addMoreSpaceForSkipButton(heightNavigation)
        }
    }

    fun loginWith(
        socialMediaType: SocialMediaType
    ) {
        when (socialMediaType) {
            SocialMediaType.ANONYMOUS -> {
                repository.loginAnonymous(
                    onSuccess = ::onLoginAnonymousSuccess,
                    onFailed = ::onLoginFailed
                )
            }
            SocialMediaType.FACEBOOK -> {
                showLoginFacebook { accessToken ->
                    repository.socialMediaAuthenticate(
                        accessToken,
                        SocialMediaType.FACEBOOK,
                        onSuccess = ::onLoginSuccess,
                        onFailed = ::onLoginFailed
                    )
                }
            }
            SocialMediaType.GOOGLE -> {
                showLoginGoogle { accessToken ->
                    repository.socialMediaAuthenticate(
                        accessToken,
                        SocialMediaType.GOOGLE,
                        onSuccess = ::onLoginSuccess,
                        onFailed = ::onLoginFailed
                    )
                }
            }
        }
    }

    fun updatePushId() {
        CoroutineScope(Dispatchers.IO).launch {
            val pushId = Pref().oneSignalId

            if (pushId != null) userRepository.updatePushIdUser(pushId)
        }
    }

    private fun onLoginAnonymousSuccess(token: Token) {
        Pref().token = token

        activity.navigateToHomePage()
    }

    private fun onLoginSuccess(token: Token) {
        activity.hideLoadingButtonFacebook()
        activity.hideLoadingButtonGoogle()

        Pref().token = token

        activity.navigateToHomePage()
    }

    private fun onLoginFailed() {
        activity.hideLoadingButtonFacebook()
        activity.hideLoadingButtonGoogle()
        CoroutineScope(Dispatchers.Main).launch { activity.showErrorMessage(R.string.login_error) }
    }

    private fun showLoginFacebook(onSuccess: (String) -> Unit) {
        authManager.doLogin(SocialMediaType.FACEBOOK, onSuccess, onFailed = { exception ->
            activity.hideLoadingButtonFacebook()
            CoroutineScope(Dispatchers.Main).launch { activity.showErrorMessage(R.string.login_facebook_error) }
        })
    }

    private fun showLoginGoogle(onSuccess: (String) -> Unit) {
        authManager.doLogin(SocialMediaType.GOOGLE, onSuccess, onFailed = { exception ->
            activity.hideLoadingButtonGoogle()

            if (exception !is SignInCancelException) {
                CoroutineScope(Dispatchers.Main).launch { activity.showErrorMessage(R.string.login_google_error) }
            }
        })
    }

}