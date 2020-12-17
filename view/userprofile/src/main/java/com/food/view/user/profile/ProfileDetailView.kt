package com.food.view.user.profile

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.CircleCropTransformation
import kotlinx.android.synthetic.main.view_user_profile_detail.view.*

/**
 * Created by mikekojansow on 21/08/20.
 * Senior Android Developer
 */
class ProfileDetailView: RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setAttributes(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setAttributes(attrs)
    }

    private val fadeDuration = 400
    private var canShowEditButton = false
    private var canShowName = true

    init {
        View.inflate(context, R.layout.view_user_profile_detail, this)
    }

    private fun setAttributes(attrs: AttributeSet?) {
        if (attrs == null) return

        val styled =  context.obtainStyledAttributes(attrs, R.styleable.ProfileView)

        canShowEditButton = styled.getBoolean(R.styleable.ProfileView_enableEdit, canShowEditButton)
        canShowName = styled.getBoolean(R.styleable.ProfileView_showName, canShowName)

        setupViews()
        styled.recycle()
    }

    private fun setupViews() {
        setupNameView()
        setupEditView()
    }

    private fun setupNameView() {
        tv_name.visibility = if (canShowName) View.VISIBLE else View.GONE
    }

    private fun setupEditView() {
        iv_edit.visibility = if (canShowEditButton) View.VISIBLE else View.GONE
    }

    fun setProfile(imageUrl: String?, name: String) {
        tv_name.text = name
        iv_profile.load(imageUrl) {
            println("coming here...")

            crossfade(fadeDuration)
            crossfade(true)

            transformations(CircleCropTransformation())

            target({
                println("drawable starting")
            }, {
                println("drawable error")
                iv_profile.setImageResource(R.drawable.ic_chef)
            }, {
                println("drawable succcess")
            })
        }
    }
}