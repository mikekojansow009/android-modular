package com.food.view.button

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.button_with_arrow.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/**
 * Created by mikekojansow on 12/08/20.
 * Senior Android Developer
 */
class ButtonWithArrow : RelativeLayout {

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

    init {
        View.inflate(context, R.layout.button_with_arrow, this)
    }

    private var btnHeight = context.resources.getDimension(R.dimen.default_height_button)
    private var textTitle = ""
    private var textColor = ContextCompat.getColor(context, R.color.text_color_black)
    private var imageIcon = R.drawable.ic_arrow
    private var fontSize = resources.getDimension(R.dimen.text_size_normal)

    private var canClick = true
    private val maxDelayTimeClicked = 1000L

    private fun setup(attrs: AttributeSet?) {
        canClick = true

        if (attrs == null) return

        val styled = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)

        btnHeight = styled.getDimension(
            R.styleable.CustomButton_height,
            btnHeight
        )

        textTitle = styled.getString(R.styleable.CustomButton_text) ?: ""
        textColor = styled.getColor(R.styleable.CustomButton_textColor, textColor)
        imageIcon = styled.getResourceId(R.styleable.CustomButton_imageIcon, imageIcon)
        fontSize = styled.getDimension(R.styleable.CustomButton_textSize, fontSize)

        if (textTitle.isEmpty()) throw Exception("Title cannot be empty!")

        styled.recycle()

        setupSubviews()
    }

    private fun setupSubviews() {
        setupButton()
        setupTitle()
        setupImage()
    }

    private fun setupButton() {
        val layoutParam = btn.layoutParams
        layoutParam.height = btnHeight.roundToInt()
    }

    private fun setupTitle() {
        tv_title.apply {
            text = textTitle
            setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
            setTextColor(textColor)
        }
    }

    private fun setupImage() {
        iv_arrow.setImageResource(imageIcon)
    }

    fun onButtonClick(action: () -> Unit) {
        setOnClickListener {
            if (canClick) action()
            else return@setOnClickListener

            canClick = false

            GlobalScope.async {
                delay(maxDelayTimeClicked)

                canClick = true
            }
        }
    }
}