package com.food.view.image

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.roundToInt

/**
 * Created by mikekojansow on 09/08/20.
 * Senior Android Developer
 */
class RatioImageView: AppCompatImageView {
    constructor(context: Context?) : super(context)
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

    private var ratioY = 0f
    private var ratioX = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (ratioY > 0f && ratioX > 0f) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = (width / ratioX * ratioY).roundToInt()
            super.onMeasure(
                MeasureSpec.makeMeasureSpec(
                    width,
                    MeasureSpec.EXACTLY
                ),
                MeasureSpec.makeMeasureSpec(
                    height,
                    MeasureSpec.EXACTLY
                )
            )
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    private fun setup(attrs: AttributeSet?) {
        attrs?.let {
            val styled =
                context.obtainStyledAttributes(attrs, R.styleable.RatioImageView)
            ratioY = styled.getFloat(R.styleable.RatioImageView_ratioY, 0f)
            ratioX = styled.getFloat(R.styleable.RatioImageView_ratioX, 0f)
            styled.recycle()
        }
    }
}