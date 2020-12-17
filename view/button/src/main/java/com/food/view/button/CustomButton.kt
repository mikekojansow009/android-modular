package com.food.view.button

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.custom_button.view.*
import kotlinx.coroutines.*

/**
 * Created by mikekojansow on 31/07/20.
 * Senior Android Developer
 */
class CustomButton : RelativeLayout {

    init {
        View.inflate(context, R.layout.custom_button, this)
    }

    constructor(context: Context?) : super(context) {
        setSubviews()
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

    companion object {
        private const val HIDE_LOADING_DELAY = 500L
        private const val ANIMATION_DELAY = 400L
        private const val CLICK_BUTTON_DELAY = 1000L
    }

    private var btnHeight = context.resources.getDimension(R.dimen.default_height_button)
    private var btnElevation = context.resources.getDimension(R.dimen.default_button_elevation)

    private var btnTextColor = ContextCompat.getColor(context, R.color.white)

    private var loadingImage = 0
    private var loadingCount = 0
    private var loadingSize = 0f

    private var btnBackground = R.drawable.default_button_background
    private var fontType = R.font.montserra_regular

    private var btnText = ""

    private var canAnimateLoading = false
    private var isTextAllCaps = false
    private var isLoading = false
    private var canClick = true

    private fun setup(attrs: AttributeSet?) {
        if (attrs == null) return

        isLoading = false
        canClick = true

        val styled = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)

        btnHeight = styled.getDimension(
            R.styleable.CustomButton_height,
            context.resources.getDimension(R.dimen.default_height_button)
        )
        btnBackground = styled.getResourceId(
            R.styleable.CustomButton_backgroundDrawable,
            R.drawable.default_button_background
        )
        btnElevation = styled.getDimension(
            R.styleable.CustomButton_buttonElevation,
            context.resources.getDimension(R.dimen.default_button_elevation)
        )
        btnTextColor = styled.getColor(
            R.styleable.CustomButton_textColor,
            ContextCompat.getColor(context, R.color.white)
        )
        loadingImage = styled.getResourceId(R.styleable.CustomButton_loadingImage, 0)
        loadingCount = styled.getInteger(R.styleable.CustomButton_loadingCount, 0)
        loadingSize = styled.getDimension(R.styleable.CustomButton_loadingSize, 0f)

        btnText = styled.getString(R.styleable.CustomButton_text) ?: ""
        canAnimateLoading = styled.getBoolean(R.styleable.CustomButton_canAnimate, false)
        isTextAllCaps = styled.getBoolean(R.styleable.CustomButton_allCaps, false)
        fontType = styled.getResourceId(R.styleable.CustomButton_fontType, R.font.montserra_regular)

        if (btnText.isEmpty()) throw Exception("Button text shouldn't be empty!")

        styled.recycle()

        setSubviews()
    }

    private fun setSubviews() {
        setupButton()
        setupLoading()
    }

    private fun setupButton() {
        btn_text.text = btnText
        btn.setBackgroundResource(btnBackground)
        btn_text.setTextColor(btnTextColor)
        btn.elevation = btnElevation
        btn_text.isAllCaps = isTextAllCaps
        btn_text.typeface = ResourcesCompat.getFont(context, fontType)

        val layoutParams = btn.layoutParams

        layoutParams.height = btnHeight.toInt()
    }

    private fun setupLoading() {
        bounce_loading.setBounceLoading(loadingSize, loadingImage, loadingCount)
    }

    private suspend fun animateLoadingIfNeeded(showLoading: Boolean) {
        if (!canAnimateLoading) return

        isLoading = showLoading

        if (showLoading) {
            bounce_loading.playLoading()
            btn.transitionToEnd()
        } else {
            delay(ANIMATION_DELAY)

            btn.transitionToStart()

            delay(HIDE_LOADING_DELAY)

            bounce_loading.stopLoading()
        }
    }

    suspend fun reEnableClick() {
        delay(CLICK_BUTTON_DELAY)

        canClick = true
    }

    fun setTextButton(text: String) {
        this.btnText = text

        setSubviews()
    }

    fun disableClick() {
        canClick = false
    }

    fun hideLoading() {
        GlobalScope.launch(Dispatchers.Main) { animateLoadingIfNeeded(showLoading = false) }
    }

    fun setClickListener(onClick: () -> Unit) {
        setOnClickListener {
            if (isLoading) return@setOnClickListener
            if (!canClick) return@setOnClickListener

            canClick = false

            GlobalScope.async {
                withContext(Dispatchers.Main) { animateLoadingIfNeeded(true) }

                reEnableClick()
            }
            onClick()
        }
    }
}