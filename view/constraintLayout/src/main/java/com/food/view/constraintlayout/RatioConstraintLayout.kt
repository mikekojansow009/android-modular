package com.food.view.constraintlayout

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.roundToInt

/**
 * Created by mikekojansow on 11/08/20.
 * Senior Android Developer
 */
class RatioConstraintLayout: ConstraintLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

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
                context.obtainStyledAttributes(attrs, R.styleable.RatioConstraintLayout)
            ratioY = styled.getFloat(R.styleable.RatioConstraintLayout_ratioY, 0f)
            ratioX = styled.getFloat(R.styleable.RatioConstraintLayout_ratioX, 0f)
            styled.recycle()
        }
    }
}