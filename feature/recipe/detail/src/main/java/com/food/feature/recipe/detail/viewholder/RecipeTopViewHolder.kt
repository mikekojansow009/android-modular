package com.food.feature.recipe.detail.viewholder

import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.food.feature.recipe.detail.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_recipe_top.*

/**
 * Created by mikekojansow on 31/08/20.
 * Senior Android Developer
 */
class RecipeTopViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val animationDuration = 400L

    var imageRecipeHeight: Int = 0
        private set

    fun setRecipe(
        recipeId: String,
        title: String,
        imageUrl: String,
        timeToCook: String,
        cuisine: String,
        description: String,
        totalLikes: Int,
        isFavorite: Boolean,
        categories: List<String>,
        onFavoriteClicked: (String) -> Unit
    ) {
        tv_recipe_title.text = title

        iv_recipe.apply {
            load(imageUrl)
            scaleType = ImageView.ScaleType.CENTER_CROP

            viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)

                    imageRecipeHeight = iv_recipe.height
                }
            })
        }

        tv_favorite_count.text = "$totalLikes"

        val favoriteResourceId = if (isFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty
        iv_like.apply {
            setImageResource(favoriteResourceId)

            setOnClickListener {
                onFavoriteClicked(recipeId)
            }
        }

        timer_view.setText("$timeToCook ${containerView.resources.getString(R.string.minutes)}")
        cook_view.setText(cuisine)
        tv_description.text = description
    }

    fun parallaxRecipeImage(scrollY: Float) {
        iv_recipe.translationY = scrollY
    }

    fun changeTotalLike(like: Int) {
        tv_favorite_count.text = "$like"
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