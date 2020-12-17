package com.food.core.utility

import android.animation.ValueAnimator

/**
 * Created by mikekojansow on 15/10/20.
 * Senior Android Developer
 */
object CustomAnimator {

    fun startDefaultAnimation(fromValue: Any, toValue: Any, animationDuration: Long = 500L, onAnimateUpdate: (Any) -> Unit) {
        var animator: ValueAnimator? = null

        if (fromValue is Float && toValue is Float) {
            animator = ValueAnimator.ofFloat(fromValue, toValue)
        } else if (fromValue is Int && toValue is Int) {
            animator = ValueAnimator.ofInt(fromValue, toValue)
        }

        if (animator == null) return

        animator.addUpdateListener {
            val value = it.animatedValue

            onAnimateUpdate(value)
        }

        animator.duration = animationDuration
        animator.start()
    }

}