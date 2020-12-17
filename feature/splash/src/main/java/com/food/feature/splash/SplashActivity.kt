package com.food.feature.splash

import android.os.Bundle
import com.food.core.base.activity.BaseActivity
import com.food.core.base.extension.flagNoLimits
import com.food.core.utility.Actions
import javax.inject.Inject

/**
 * Created by mikekojansow on 24/07/20.
 * Senior Android Developer
 */
class SplashActivity : BaseActivity(R.layout.splash) {

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.flagNoLimits()

        DaggerSplashComponent.builder().splashModule(SplashModule(this)).build().inject(this)

        presenter.navigateToNextPage()
    }

    fun navigateToLoginPage() {
        Actions.navigateToLoginPage(this)
    }

    fun navigateToHomePage() {
        Actions.navigateToHomePage(this)
    }

}