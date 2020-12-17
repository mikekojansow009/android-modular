package com.food.sub.profile

import android.os.Bundle
import android.view.View
import com.food.core.base.fragment.BaseFragment
import com.food.core.dependecy.di.module.ContextModule
import com.food.core.preferences.Pref
import com.food.core.utility.Actions
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.profile_view.view.*
import javax.inject.Inject

/**
 * Created by mikekojansow on 12/08/20.
 * Senior Android Developer
 */
class ProfileFragment : BaseFragment(R.layout.profile_fragment) {

    @Inject
    lateinit var presenter: ProfilePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerProfileComponent.builder().contextModule(ContextModule(context!!))
            .profileModule(ProfileModule(this)).build().inject(this)

        setSubviews()

        presenter.getUserData()
    }

    override fun onResume() {
        super.onResume()

        if (::presenter.isInitialized) presenter.getUserData()
    }

    private fun setSubviews() {
        onViewClick()
    }

    private fun onViewClick() {
        btn_account.onButtonClick {
            Actions.navigateToEditProfilePage(context!!)
        }

        btn_notification.onButtonClick {

        }

        btn_preferences.onButtonClick {

        }

        btn_about_us.onButtonClick {
            Pref().userPref?.let { userPref ->
                Actions.navigateToWebView(context!!, userPref.aboutUsUrl)
            }
        }
    }

    fun setProfile(name: String, imageUrl: String?, follower: Int, following: Int) {
        layout_profile.tv_follower.text = follower.toString()
        layout_profile.tv_following.text = following.toString()
        layout_profile.profile_view.setProfile(imageUrl, name)
    }
}