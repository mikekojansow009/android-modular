package com.food.feature.login

import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.food.core.auth.AuthManager
import com.food.core.auth.SocialMediaType
import com.food.core.base.activity.BaseActivity
import com.food.core.base.extension.flagNoLimits
import com.food.core.dependecy.di.module.ContextModule
import com.food.core.utility.Actions
import com.food.dialog.error.DialogError
import kotlinx.android.synthetic.main.login.*
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Created by mikekojansow on 30/07/20.
 * Senior Android Developer
 */
class LoginActivity : BaseActivity(R.layout.login) {

    @Inject lateinit var presenter: LoginPresenter

    @Inject lateinit var authManager: AuthManager

    companion object {
        private const val ERROR_SHOW_DELAY = 700L
        private const val TOKEN_EXPIRED = "TOKEN_EXPIRED"
        private const val DIALOG_EXPIRED = "DIALOG_EXPIRED"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.flagNoLimits()

        DaggerLoginComponent.builder().loginModule(LoginModule(this)).contextModule(ContextModule(this)).build().inject(this)

        layout.transitionToEnd()

        setupSubviews()

        presenter.showTokenExpiredIfNeeded(intent.getBooleanExtra(TOKEN_EXPIRED, false))
        presenter.addSpaceIfHasNavigationButton()
        presenter.updatePushId()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupSubviews() {
        setBtnFacebook()
        setBtnGoogle()
        setBtnAnonymous()
    }

    private fun setBtnFacebook() {
        btn_login_facebook.setClickListener {
            btn_login_google.disableClick()
            presenter.loginWith(socialMediaType = SocialMediaType.FACEBOOK)
        }
    }

    private fun setBtnGoogle() {
        btn_login_google.setClickListener {
            btn_login_facebook.disableClick()
            presenter.loginWith(socialMediaType = SocialMediaType.GOOGLE)
        }
    }

    private fun setBtnAnonymous() {
        tv_skip.setOnClickListener {
            presenter.loginWith(
                socialMediaType = SocialMediaType.ANONYMOUS)
        }
    }

    fun addMoreSpaceForSkipButton(space: Int) {
        val layoutParams = tv_skip.layoutParams as ConstraintLayout.LayoutParams

        val newBottomMargin = layoutParams.bottomMargin + space

        layout.getConstraintSet(R.id.end)?.setMargin(R.id.tv_skip, 4, newBottomMargin)
    }

    fun hideLoadingButtonFacebook() {
        btn_login_facebook.hideLoading()
        reEnableAllButton()
    }

    fun hideLoadingButtonGoogle() {
        btn_login_google.hideLoading()
        reEnableAllButton()
    }

    fun showDialogExpired() {
        var dialog = supportFragmentManager.findFragmentByTag(DIALOG_EXPIRED) as? DialogError

        if (dialog == null) {
            dialog = DialogError(resources.getString(R.string.session_expired), resources.getString(R.string.session_expired_msg), resources.getString(R.string.close))

            dialog.show(supportFragmentManager, DIALOG_EXPIRED)
        }
    }

    suspend fun showErrorMessage(messageId: Int) {
        val dialog = DialogError(resources.getString(R.string.title_failed), resources.getString(messageId), resources.getString(R.string.title_close))

        delay(ERROR_SHOW_DELAY)
        dialog.show(supportFragmentManager, LoginActivity::class.java.simpleName)
    }

    fun navigateToHomePage() {
        Actions.navigateToHomePage(this)
    }

    private fun reEnableAllButton() {
        CoroutineScope(Dispatchers.Main).launch {
            btn_login_facebook.reEnableClick()
            btn_login_google.reEnableClick()
        }
    }

}