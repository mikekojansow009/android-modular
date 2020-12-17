package com.food.sub.main

import android.view.animation.PathInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by mikekojansow on 25/08/20.
 * Senior Android Developer
 */
class MainItemAnimator: DefaultItemAnimator() {

    override fun canReuseUpdatedViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ): Boolean {
        return true
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        println("Animating add")
        if (holder != null) runItemAddedAnimation(holder)

        return false
    }

    private fun runItemAddedAnimation(viewHolder: RecyclerView.ViewHolder) {
        println("Start animation item added")
        viewHolder.itemView.translationX = viewHolder.itemView.width * 2f

        viewHolder.itemView.animate().apply {
            interpolator = PathInterpolator(2f, 1f, 2f,1f)
            duration = 600
            translationX(0f)

            println("Start animating")
        }.start()
    }
}