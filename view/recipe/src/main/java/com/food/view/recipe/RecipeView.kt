package com.food.view.recipe

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.RelativeLayout
import coil.load
import coil.transform.RoundedCornersTransformation
import com.food.recipe.common.database.model.Recipe
import com.food.core.utility.TimeExpressionUtils
import kotlinx.android.synthetic.main.recipe_view.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

/**
 * Created by mikekojansow on 10/08/20.
 * Senior Android Developer
 */
class RecipeView: RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val fadeDuration = 300
    private val clickedDelay = 1000L
    private val animationDuration = 400L

    init {
        View.inflate(context, R.layout.recipe_view, this)
    }

    private var recipe: Recipe? = null
    private var isLikePressed = false
    private var isRecipePressed = false

    private fun setViews() {
        recipe?.let { recipe ->
            tv_title.text = recipe.name
            tv_preparation_time.text = recipe.preparationTime
            tv_total_like.text = "${recipe.likes}"

            iv_recipe.load(recipe.imageUrl) {
                crossfade(fadeDuration)
                crossfade(true)

                transformations(RoundedCornersTransformation(resources.getDimension(R.dimen.image_recipe_rounded)))
            }

            profile_view.setUserProfile(recipe.user)

            if (recipe.isFavorite) playAnimationLikeIfNeeded(false)
            else playAnimationDislikeIfNeeded(false)
        }
    }

    fun setRecipe(recipe: Recipe) {
        this.recipe = recipe

        setViews()
    }

    fun setOnFavoriteClicked(onFavoriteClickListener: (String?) -> Unit) {
        iv_like.setOnClickListener {
            if (isLikePressed) return@setOnClickListener

            onFavoriteClickListener(recipe?.id)

            isLikePressed = true

            GlobalScope.async {
                delay(clickedDelay)

                isLikePressed = false
            }
        }
    }

    fun setOnRecipeClicked(onRecipeClicked: (Recipe) -> Unit) {
        setOnClickListener {
            if (recipe == null) return@setOnClickListener

            if (isRecipePressed) return@setOnClickListener

            recipe?.let(onRecipeClicked)

            isRecipePressed = true

            GlobalScope.async {
                delay(clickedDelay)

                isRecipePressed = false
            }
        }
    }

    fun changeTotalLike(like: Int) {
        tv_total_like.text = "$like"
    }

    fun playAnimationLike() {
        playAnimationLikeIfNeeded(true)
    }

    fun playAnimationDislike() {
        playAnimationDislikeIfNeeded(true)
    }

    private fun playAnimationLikeIfNeeded(playAnimation: Boolean) {
        iv_like.setImageResource(R.drawable.ic_heart)

        if (!playAnimation) return

        playAnimationScaling()
    }

    private fun playAnimationDislikeIfNeeded(playAnimation: Boolean) {
        iv_like.setImageResource(R.drawable.ic_heart_empty)

        if (!playAnimation) return

        playAnimationScaling()
    }

    private fun playAnimationScaling() {
        val animation = ScaleAnimation(1f, 1.3f, 1f, 1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = animationDuration
        animation.repeatMode = Animation.REVERSE
        animation.repeatCount = 1
        iv_like.startAnimation(animation)
    }
}