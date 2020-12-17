package com.food.feature.recipe.detail

import com.food.core.dependecy.di.module.ContextModule
import dagger.Component

/**
 * Created by mikekojansow on 30/08/20.
 * Senior Android Developer
 */
@Component(modules = [ContextModule::class, RecipeDetailModule::class])
interface RecipeDetailComponent {
    fun inject(recipeDetailDialog: RecipeDetailActivity)
}