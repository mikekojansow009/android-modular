package com.food.view.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.custom_toolbar.view.*

/**
 * Created by mikekojansow on 27/09/20.
 * Senior Android Developer
 */
class CustomToolbar: RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init (attrs)
    }

    private var title = ""
    private var colorBackground = R.color.white
    private var useAnimation = false

    var onBackPressed = {}

    init {
        View.inflate(context, R.layout.custom_toolbar, this)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs == null) return

        val styled = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)

        title = styled.getString(R.styleable.CustomToolbar_title) ?: ""
        colorBackground = styled.getInt(R.styleable.CustomToolbar_colorBackground, colorBackground)

        styled.recycle()

        setupView()
    }

    private fun setupView() {
        setTitleView()
        setIconClicked()
        setBackgroundView()
    }

    private fun setBackgroundView() {
        custom_toolbar_layout.setBackgroundColor(ContextCompat.getColor(context, colorBackground))
    }

    private fun setTitleView() {
        tv_title.text = title
    }

    private fun setIconClicked() {
        iv_arrow_back.setOnClickListener {
            onBackPressed()
        }
    }

    fun setTitle(title: String) {
        this.title = title

        setTitleView()
    }

    fun setColorBackground(backgroundColor: Int) {
        colorBackground = backgroundColor

        setBackgroundView()
    }

    fun enableAnimation() {
        useAnimation = true
    }

}