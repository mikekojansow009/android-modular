package com.food.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.viewpager2.widget.ViewPager2
import com.food.core.base.activity.AuthActivity
import com.food.core.dependecy.di.module.ContextModule
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

/**
 * Created by mikekojansow on 08/08/20.
 * Senior Android Developer
 */
class HomeActivity: AuthActivity(R.layout.activity_home) {

    @Inject lateinit var presenter: HomePresenter

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerHomeComponent.builder().contextModule(ContextModule(applicationContext)).homeModule(HomeModule((this))).build().inject(this)

        setSubviews()

        presenter.loadUserProfile()
        presenter.updatePushId()
    }

    override fun onResume() {
        super.onResume()
        currentFragment?.onResume()
    }

    private fun setSubviews() {
        setViewClicked()
        setViewPager()
    }

    private fun setViewClicked() {
        selectHomeMenu()

        item_home.setNavigationClick {
            selectHomeMenu()
        }

        item_favorite.setNavigationClick {
            selectFavoriteMenu()
        }

        item_search.setNavigationClick {
            selectSearchMenu()
        }

        item_profile.setNavigationClick {
            selectProfileMenu()
        }
    }

    private fun setViewPager() {
        view_pager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL

            adapter = HomeAdapter(this@HomeActivity) {
                selectHomeMenu()
            }

            isUserInputEnabled = false

            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    println("Current Position : $position")
                    when (position) {
                        HomeAdapter.MAIN_SECTION -> selectHomeMenu()
                        HomeAdapter.FAVORITE_SECTION -> selectFavoriteMenu()
                        HomeAdapter.PROFILE_SECTION -> selectProfileMenu()
                        HomeAdapter.SEARCH_SECTION -> selectSearchMenu()
                    }
                }
            })
        }
    }

    private fun selectHomeMenu() {
        item_home.selectView()
        item_favorite.deselectView()
        item_search.deselectView()
        item_profile.deselectView()
        
        view_pager.currentItem = HomeAdapter.MAIN_SECTION
    }

    private fun selectFavoriteMenu() {
        item_favorite.selectView()
        item_home.deselectView()
        item_search.deselectView()
        item_profile.deselectView()

        view_pager.currentItem = HomeAdapter.FAVORITE_SECTION
    }

    private fun selectSearchMenu() {
        item_search.selectView()
        item_home.deselectView()
        item_favorite.deselectView()
        item_profile.deselectView()
    
        view_pager.currentItem = HomeAdapter.SEARCH_SECTION
    }

    private fun selectProfileMenu() {
        item_profile.selectView()
        item_home.deselectView()
        item_favorite.deselectView()
        item_search.deselectView()
    
        view_pager.currentItem = HomeAdapter.PROFILE_SECTION
    }
}