package com.food.view.image

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.image_with_text.view.*

/**
 * Created by mikekojansow on 03/09/20.
 * Senior Android Developer
 */
class ImageWithTextView: RelativeLayout {

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

    private var imageSize = resources.getDimensionPixelSize(R.dimen.image_size)
    private var imageResource = 0
    private var fontColor = ContextCompat.getColor(context, R.color.text_black)
    private var fontSize = resources.getDimension(R.dimen.text_size_medium)
    private var fontStyle = R.font.montserra_regular
    private var textTitle = ""

    init {
        View.inflate(context, R.layout.image_with_text, this)
    }

    private fun setup(attrs: AttributeSet?) {
        if (attrs == null) return

        val styled = context.obtainStyledAttributes(attrs, R.styleable.ImageWithTextView)

        imageSize = styled.getDimensionPixelSize(R.styleable.ImageWithTextView_imageSize, imageSize)
        imageResource = styled.getResourceId(R.styleable.ImageWithTextView_imageSrc, imageResource)
        fontColor = styled.getColor(R.styleable.ImageWithTextView_fontColor, fontColor)
        fontSize = styled.getDimension(R.styleable.ImageWithTextView_fontSize, fontSize)
        fontStyle = styled.getResourceId(R.styleable.ImageWithTextView_font, fontStyle)
        textTitle = styled.getString(R.styleable.ImageWithTextView_text) ?: ""

        setupViews()

        styled.recycle()
    }

    private fun setupViews() {
        setupText()
        setupImage()
    }

    private fun setupText() {
        tv_title.apply {
            text = textTitle
            setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
            setTextColor(fontColor)
        }
    }

    private fun setupImage() {
        iv.apply {
            if (imageResource != 0) setImageResource(imageResource)

            layoutParams.width = imageSize
            layoutParams.height = imageSize

            requestLayout()
        }
    }

    fun setText(text: String) {
        textTitle = text

        setupText()
    }
}