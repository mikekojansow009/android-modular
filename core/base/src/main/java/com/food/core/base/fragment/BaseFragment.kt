package com.food.core.base.fragment

import androidx.fragment.app.Fragment

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
open class BaseFragment(private val layoutResourceId: Int): Fragment(layoutResourceId) {
    var hasDestroyed = true

    override fun onResume() {
        super.onResume()
        hasDestroyed = false
        println("$this resuming view : $hasDestroyed")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        hasDestroyed = true
        println("$this destroying view : $hasDestroyed")
    }
}