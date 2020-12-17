package com.food.feature.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.food.sub.favorite.FavoriteFragment
import com.food.sub.main.MainFragment
import com.food.sub.profile.ProfileFragment
import com.food.sub.search.SearchFragment

/**
 * Created by mikekojansow on 19/11/20.
 * Senior Android Developer
 */
class HomeAdapter(val activity: FragmentActivity, val onFindRecipe: () -> Unit): FragmentStateAdapter(activity) {

    companion object {
        private const val MAX_ITEMS = 4
        const val MAIN_SECTION = 0
        const val FAVORITE_SECTION = 1
        const val SEARCH_SECTION = 2
        const val PROFILE_SECTION = 3
    }

    override fun getItemCount(): Int {
        return MAX_ITEMS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            FAVORITE_SECTION -> FavoriteFragment(onFindRecipeClicked = onFindRecipe)
            SEARCH_SECTION -> SearchFragment()
            PROFILE_SECTION -> ProfileFragment()
            else -> MainFragment()
        }
    }
}