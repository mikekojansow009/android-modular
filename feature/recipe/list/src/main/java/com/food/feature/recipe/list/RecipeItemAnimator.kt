package com.food.feature.recipe.list

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by mikekojansow on 29/09/20.
 * Senior Android Developer
 */
class RecipeItemAnimator: DefaultItemAnimator() {

    companion object {
        private const val ANIMATION_DURATION = 500L
        private const val DELAY_DURATION = 80L
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        if (holder != null) doAnimationItem(holder)

        return false
    }

    private fun doAnimationItem(holder: RecyclerView.ViewHolder) {
        val itemPosition = holder.adapterPosition
        val maxDelayDuration = itemPosition * DELAY_DURATION
        val view = holder.itemView

        view.translationX = view.width.toFloat()
        view.animate().apply {
            translationX(0f)
            startDelay = maxDelayDuration
            duration = ANIMATION_DURATION

            setListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    dispatchAddFinished(holder)
                }
            })
        }.start()
    }
}