package com.food.view.recipe

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import coil.load
import coil.transform.RoundedCornersTransformation
import com.food.core.utility.TimeExpressionUtils
import com.food.recipe.common.database.model.Recipe
import kotlinx.android.synthetic.main.recipe_potrait_view.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

/**
 * Created by mikekojansow on 23/08/20.
 * Senior Android Developer
 */
class RecipePotraitView: RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        View.inflate(context, R.layout.recipe_potrait_view, this)
    }

    private var recipe: Recipe? = null
    private val fadeDuration = 400
    private var isRecipePressed = false
    private val clickedDelay = 1000L

    private fun setViews() {
        isRecipePressed = false

        recipe?.let { recipe ->
            tv_title.text = recipe.name
            tv_preparation_time.text = recipe.preparationTime

            iv_recipe.load(recipe.imageUrl) {
                crossfade(fadeDuration)
                crossfade(true)

                val radius = resources.getDimension(R.dimen.image_recipe_rounded)

                transformations(RoundedCornersTransformation(radius, radius, 0f, 0f))
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

    fun setRecipe(recipe: Recipe) {
        this.recipe = recipe

        setViews()
    }
}