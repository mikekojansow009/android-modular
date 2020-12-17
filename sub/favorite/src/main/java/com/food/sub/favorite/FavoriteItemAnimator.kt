package com.food.sub.favorite

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.animation.DecelerateInterpolator
import android.view.animation.PathInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by mikekojansow on 25/08/20.
 * Senior Android Developer
 */
class FavoriteItemAnimator: DefaultItemAnimator() {

    companion object {
        private const val TAG_ANIMATION_DURATION = 400L
        private const val MAX_DELAYED_DURATION = 150L
    }

    override fun canReuseUpdatedViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ): Boolean {
        return true
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        return true
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        if (holder != null) runItemAddedAnimation(holder)

        return false
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        return true
    }

    private fun runItemAddedAnimation(viewHolder: RecyclerView.ViewHolder) {
        val itemPosition = viewHolder.adapterPosition
        val itemDelayed = itemPosition * MAX_DELAYED_DURATION

        viewHolder.itemView.translationY = viewHolder.itemView.height * -1f
        viewHolder.itemView.alpha = 0f

        viewHolder.itemView.animate().apply {
            // ToDo: Do Animation Added in here
            translationY(0f)
            translationX(0f)
            interpolator = PathInterpolator(1f, 1f)
            duration = TAG_ANIMATION_DURATION
            alpha(1f)
            setListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    dispatchAddFinished(viewHolder)
                }
            })
            startDelay = itemDelayed
        }.start()
    }
}