package com.food.sub.main.viewholder

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.food.recipe.common.database.model.Recipe
import com.food.sub.main.content.ContentFragment

/**
 * Created by mikekojansow on 11/08/20.
 * Senior Android Developer
 */
class ContentAdapter(private val recipes: List<Recipe>, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val currentFragment = ContentFragment()
        val bundle = Bundle()
        bundle.putParcelable(ContentFragment.TAG_RECIPE, recipes[position])

        currentFragment.arguments = bundle

        return currentFragment
    }

    override fun getCount(): Int {
        return recipes.size
    }

}