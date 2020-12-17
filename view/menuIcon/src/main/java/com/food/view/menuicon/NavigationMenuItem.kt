package com.food.view.menuicon

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.android.synthetic.main.navigation_item.view.*

/**
 * Created by mikekojansow on 08/08/20.
 * Senior Android Developer
 */
class NavigationMenuItem : RelativeLayout {

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

    constructor(context: Context?) : super(context)

    init {
        View.inflate(context, R.layout.navigation_item, this)
    }

    private var imageSize = resources.getDimensionPixelSize(R.dimen.default_icon_navigation_size)
    private var imageResource = 0
    private var fontColor = ContextCompat.getColor(context, R.color.text_black)
    private var fontType = R.font.montserrat_semibold
    private var fontSize = resources.getDimension(R.dimen.text_size_small)
    private var text = ""
    private var backgroundItemSelectedColor = ContextCompat.getColor(context, R.color.default_selected_item)
    private var backgroundItemNormalColor = ContextCompat.getColor(context, R.color.default_normal_item)
    private var textSelectedColor = ContextCompat.getColor(context, R.color.white)
    private var imageSelectedColor = 0
    private var imageNormalColor = 0
    private var imageResourceSelected = 0

    private fun setup(attrs: AttributeSet?) {
        if (attrs == null) return

        val styled = context.obtainStyledAttributes(attrs, R.styleable.NavigationMenuItem)

        fontType = styled.getResourceId(R.styleable.NavigationMenuItem_fontType, fontType)
        fontColor = styled.getColor(R.styleable.NavigationMenuItem_textColor, fontColor)
        fontSize = styled.getDimension(R.styleable.NavigationMenuItem_textSize, fontSize)
        imageSize =
            styled.getDimensionPixelSize(R.styleable.NavigationMenuItem_imageSize, imageSize)
        imageResource = styled.getResourceId(R.styleable.NavigationMenuItem_imageSource, 0)
        text = styled.getString(R.styleable.NavigationMenuItem_text) ?: ""
        backgroundItemNormalColor = styled.getColor(R.styleable.NavigationMenuItem_backgroundItemNormalColor, backgroundItemNormalColor)
        backgroundItemSelectedColor = styled.getColor(R.styleable.NavigationMenuItem_backgroundItemSelectedColor, backgroundItemSelectedColor)
        textSelectedColor = styled.getColor(R.styleable.NavigationMenuItem_textSelectedColor, textSelectedColor)
        imageSelectedColor = styled.getColor(R.styleable.NavigationMenuItem_imageSelectedColor, imageSelectedColor)
        imageNormalColor = styled.getColor(R.styleable.NavigationMenuItem_imageNormalSelected, imageNormalColor)
        imageResourceSelected = styled.getResourceId(R.styleable.NavigationMenuItem_imageSourceSelected, 0)

        styled.recycle()

        setSubviews()
    }

    private fun setSubviews() {
        setTextView()
        setImageView()
    }

    private fun setImageView() {
        iv_icon.apply {
            if (imageResource != 0) setImageResource(imageResource)

            layoutParams.height = imageSize
            layoutParams.width = imageSize
        }
    }

    private fun setTextView() {
        tv_item.apply {
            setTextColor(fontColor)

            text = this@NavigationMenuItem.text

            val typeFace = ResourcesCompat.getFont(context, fontType)
            typeface = typeFace
        }
    }

    private fun playAnimationSelected() {
        navigation.transitionToEnd()
    }

    private fun playAnimationDeselected() {
        navigation.transitionToStart()
    }

    private fun setViewSelected() {
        navigation.getConstraintSet(R.id.end).apply {
            setColorValue(navigation.id, "BackgroundColor", backgroundItemSelectedColor)
            setColorValue(R.id.tv_item, "TextColor", textSelectedColor)
        }

        iv_icon.apply {
            if (imageResourceSelected != 0) setImageResource(imageResourceSelected)

            colorFilter = if (imageSelectedColor != 0) BlendModeColorFilterCompat.createBlendModeColorFilterCompat(imageSelectedColor, BlendModeCompat.SRC_IN) else null
        }

        playAnimationSelected()
    }

    private fun setViewUnselected() {
        navigation.getConstraintSet(R.id.start).apply {
            setColorValue(navigation.id, "BackgroundColor", backgroundItemNormalColor)
            setColorValue(tv_item.id, "TextColor", fontColor)
        }

        iv_icon.apply {
            if (imageResourceSelected != 0 && imageResource != 0) {
                setImageResource(imageResource)
            }

            colorFilter = if (imageNormalColor == 0) null else BlendModeColorFilterCompat.createBlendModeColorFilterCompat(imageNormalColor, BlendModeCompat.SRC_IN)
        }

        playAnimationDeselected()
    }

    fun setNavigationClick(onClick: () -> Unit) {
        setOnClickListener {
            onClick()
            setViewSelected()
        }
    }

    fun selectView() {
        setViewSelected()
    }

    fun deselectView() {
        setViewUnselected()
    }

    fun setText(text: String) {
        this.text = text
        setTextView()
    }

    fun setImage(imageResource: Int) {
        this.imageResource = imageResource

        setImageView()
    }
}