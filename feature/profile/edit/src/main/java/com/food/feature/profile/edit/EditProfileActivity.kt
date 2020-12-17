package com.food.feature.profile.edit

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.MenuItem
import com.food.core.base.activity.AuthActivity
import com.food.core.dependecy.di.module.ContextModule
import com.food.core.model.extension.onTextChanged
import com.food.core.utility.Actions
import com.food.dialog.error.DialogError
import com.food.dialog.success.DialogSuccess
import com.food.feature.question.DialogQuestion
import com.food.recipe.common.database.model.User
import kotlinx.android.synthetic.main.activity_edit_profile.*
import javax.inject.Inject

/**
 * Created by mikekojansow on 21/08/20.
 * Senior Android Developer
 */
class EditProfileActivity: AuthActivity(R.layout.activity_edit_profile) {

    companion object {
        private const val ANIMATION_DURATION = 400L

        private const val DIALOG_ERROR = "dialog_error"
        private const val DIALOG_SUCCESS = "dialog_success"
        private const val DIALOG_LOGOUT = "dialog_logout"
    }

    @Inject lateinit var presenter: EditProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerEditProifleComponent.builder().contextModule(ContextModule(this)).editProfileModule(EditProfileModule(this)).build().inject(this)

        setViews()

        presenter.setupData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setViews() {
        setToolbar()
        setupEditTextView()
        setupButton()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.edit_profile)
    }

    private fun setupEditTextView() {
        et_about_me.onTextChanged { aboutMe ->
            presenter.validateChangesUserData(et_name.text.toString(), aboutMe)
        }

        et_name.onTextChanged { name ->
            presenter.validateChangesUserData(name, et_about_me.text.toString())
        }
    }

    private fun setupButton() {
        btn_save.setClickListener {
            presenter.requestUpdateUser(et_name.text.toString(), et_about_me.text.toString())
        }

        btn_logout.onButtonClick {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        var questionDialog = supportFragmentManager.findFragmentByTag(DIALOG_LOGOUT) as? DialogQuestion

        if (questionDialog == null) {
            questionDialog = DialogQuestion(resources.getString(R.string.logout), resources.getString(R.string.logout_message_dialog), onButtonCancelClicked = {}, onButtonOkClicked = {
                presenter.logoutUser()
            })

            questionDialog.show(supportFragmentManager, DIALOG_LOGOUT)
        }
    }

    fun navigateToLogin() {
        Actions.navigateToLoginPage(this)
    }

    fun setVersionName(version: String) {
        tv_version.text = version
    }

    fun setUser(user: User) {
        profile_view.setProfile(user.imageUrl, "")
        et_name.setText(user.getFullName())
        et_email.setText(user.email)

        et_about_me.setText(user.bio)
    }

    fun showSaveButton() {
        val valueAnimator = ValueAnimator.ofInt(0, resources.getDimensionPixelSize(R.dimen.base_btn_height))
        valueAnimator.duration = ANIMATION_DURATION
        valueAnimator.addUpdateListener { anim ->
            val btnHeight = anim.animatedValue as Int

            btn_save.layoutParams.height = btnHeight

            btn_save.requestLayout()
        }

        valueAnimator.start()
    }

    fun hideSaveButton() {
        val valueAnimator = ValueAnimator.ofInt(resources.getDimensionPixelSize(R.dimen.base_btn_height), 0)
        valueAnimator.duration = ANIMATION_DURATION
        valueAnimator.addUpdateListener { anim ->
            val btnHeight = anim.animatedValue as Int

            btn_save.layoutParams.height = btnHeight

            btn_save.requestLayout()
        }

        valueAnimator.start()
    }

    fun getHeightCurrentSaveButton(): Int {
        return btn_save.layoutParams.height
    }

    fun hideLoadingButton() {
        btn_save.hideLoading()
    }

    fun showUpdateSuccess() {
        var dialog = supportFragmentManager.findFragmentByTag(DIALOG_SUCCESS) as? DialogSuccess

        if (dialog == null) {
            dialog = DialogSuccess(resources.getString(R.string.success), resources.getString(R.string.update_profile), resources.getString(R.string.close))
            dialog.show(supportFragmentManager, DIALOG_SUCCESS)
        }
    }

    fun showUpdateFailed() {
        var dialog = supportFragmentManager.findFragmentByTag(DIALOG_ERROR) as? DialogError

        if (dialog == null) {
            dialog = DialogError(resources.getString(R.string.success), resources.getString(R.string.update_profile), resources.getString(R.string.close))
            dialog.show(supportFragmentManager, DIALOG_ERROR)
        }
    }
}