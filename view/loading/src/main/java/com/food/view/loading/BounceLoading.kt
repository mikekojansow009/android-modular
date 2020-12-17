package com.food.view.loading

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import kotlin.math.roundToInt

/**
 * Created by mikekojansow on 03/08/20.
 * Senior Android Developer
 */
class BounceLoading: LinearLayout {

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    constructor(context: Context?) : super(context) {
        setBounceView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setup(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setup(attrs)
    }

    private val durationDelay = 60L

    private var animationTime = 450L

    private var size = resources.getDimension(R.dimen.small_icon_loading)
    private var image = R.drawable.circle_white
    private var count = 3

    private var currentAnimations = arrayListOf<ValueAnimator>()
    private var imageViewList = arrayListOf<ImageView>()

    private fun setup(attrs: AttributeSet?) {
        if (attrs == null) return

        val styled = context.obtainStyledAttributes(attrs, R.styleable.BounceLoading)

        size = styled.getDimension(R.styleable.BounceLoading_size, size)
        image = styled.getResourceId(R.styleable.BounceLoading_image, image)
        count = styled.getInteger(R.styleable.BounceLoading_count, count)

        styled.recycle()

        setSubviews()
    }

    private fun setSubviews() {
        setLinearLayoutHeight()
        setBounceView()
    }

    private fun setLinearLayoutHeight() {
        minimumHeight = (size * 3).roundToInt()
        requestLayout()
    }

    private fun setBounceView() {
        removeAllViews()

        for (index in 0 until count) {
            val imageView = ImageView(context)
            setBounceImage(imageView)
            addView(imageView)
            setBounceSize(imageView)

            imageViewList.add(imageView)
        }

        setAnimation()
    }

    private fun setBounceSize(imageView: ImageView) {
        val layoutParams = imageView.layoutParams as LayoutParams

        layoutParams.height = size.roundToInt()
        layoutParams.width = size.roundToInt()
        layoutParams.marginEnd = resources.getDimension(R.dimen.padding_small).roundToInt()
    }

    private fun setBounceImage(imageView: ImageView) {
        imageView.setImageResource(image)
    }

    private fun setAnimation() {
        currentAnimations.clear()

        var delay = 0L

        for ((index, imageView) in imageViewList.withIndex()) {
            val animation = ObjectAnimator.ofFloat(size / 2, size * -1)
            animation.repeatMode = ValueAnimator.REVERSE
            animation.repeatCount = ValueAnimator.INFINITE
            animation.duration = this.animationTime
            animation.startDelay = delay

            animation.addUpdateListener { valueAnimator ->
                val movingValue = valueAnimator?.animatedValue as Float

                imageView.translationY = movingValue
            }

            delay = (index + 1) * durationDelay

            currentAnimations.add(animation)
        }
    }

    fun setBounceLoading(size: Float, imageResource: Int, imageCount: Int) {
        if (size > 0) this.size = size

        if (imageResource != 0) this.image = imageResource

        if (imageCount != 0) this.count = imageCount

        setSubviews()
    }

    fun setAnimationTime(animationTime: Long) {
        this.animationTime = animationTime
    }

    fun playLoading() {
        currentAnimations.forEach { animator ->
            animator.start()
        }
    }

    fun stopLoading() {
        currentAnimations.forEach { animator ->
            animator.pause()
        }
    }
}