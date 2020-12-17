package com.food.view.user.profile

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.food.recipe.common.database.model.User
import kotlinx.android.synthetic.main.view_user_profile.view.*
import kotlin.math.roundToInt

/**
 * Created by mikekojansow on 17/08/20.
 * Senior Android Developer
 */
class ProfileView: RelativeLayout {

    companion object {
        private const val FADE_DURATION = 500
    }

    constructor(context: Context?) : super(context) { setup(null) }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { setup(attrs) }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) { setup(attrs) }

    private var fontColor = ContextCompat.getColor(context, R.color.text_black)
    private var fontSize = resources.getDimension(R.dimen.text_size_medium)
    private var fontFamily = R.font.helvetica_now_medium
    private var imageSize = resources.getDimension(R.dimen.image_height)

    init {
        View.inflate(context, R.layout.view_user_profile, this)
    }

    private fun setup(attrs: AttributeSet?) {
        if (attrs == null) {
            setViews()
            return
        }

        val styled = context.obtainStyledAttributes(attrs, R.styleable.ProfileView)

        fontColor = styled.getColor(R.styleable.ProfileView_fontColor, fontColor)
        fontSize = styled.getDimension(R.styleable.ProfileView_fontSize, fontSize)
        fontFamily = styled.getResourceId(R.styleable.ProfileView_fontFamilyProfile, fontFamily)
        imageSize = styled.getDimension(R.styleable.ProfileView_imageSize, imageSize)

        setViews()

        styled.recycle()
    }

    private fun setViews() {
       setImageView()
        setTextView()
    }

    private fun setImageView() {
        val layoutParams = iv_profile.layoutParams
        layoutParams.apply {
            height = imageSize.roundToInt()
            width = imageSize.roundToInt()
        }

        iv_profile.layoutParams = layoutParams
    }

    private fun setTextView() {
        tv_profile.apply {
            setTextColor(fontColor)
            typeface = ResourcesCompat.getFont(context, fontFamily)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
        }
    }

    fun setUserProfile(user: User) {
        iv_profile.load(user.imageUrl) {
            crossfade(FADE_DURATION)
            transformations(CircleCropTransformation())
        }

        tv_profile.text = user.getFullName()
    }
}