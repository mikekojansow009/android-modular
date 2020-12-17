package com.food.feature.recipe.list

import com.food.core.dependecy.di.module.ContextModule
import dagger.Component

/**
 * Created by mikekojansow on 05/09/20.
 * Senior Android Developer
 */
@Component(modules = [RecipeListModule::class, ContextModule::class])
interface RecipeListComponent {

    fun injectActivity(activity: RecipeListActivity)

}