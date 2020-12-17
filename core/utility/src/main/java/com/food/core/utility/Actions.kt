package com.food.core.utility

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.food.core.model.extension.newTaskActivity

/**
 * Created by mikekojansow on 31/07/20.
 * Senior Android Developer
 */
object Actions {

    fun navigateToLoginPage(context: Context, isTokenExpired: Boolean = false) {
        val intent = Intent("feature.login")
        intent.setPackage(context.packageName)
        intent.newTaskActivity()
        intent.putExtra("TOKEN_EXPIRED", isTokenExpired)
        context.startActivity(intent)
    }

    fun navigateToHomePage(context: Context) {
        val intent = Intent("feature.home")
        intent.setPackage(context.packageName)
        intent.newTaskActivity()
        context.startActivity(intent)
    }

    fun navigateToRecipeDetail(context: Context, recipe: Parcelable) {
        val intent = Intent("feature.recipe.detail")
        intent.setPackage(context.packageName)
        intent.putExtra("RECIPE", recipe)
        context.startActivity(intent)
    }

    fun navigateToRecipeDetailFromNotification(context: Context, id: String) {
        val intent = Intent("feature.recipe.detail")
        intent.setPackage(context.packageName)
        intent.putExtra("RECIPE_ID", id)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK

        context.startActivity(intent)
    }

    fun navigateToWebView(context: Context, url: String) {
        val intent = Intent("feature.web.view")
        intent.setPackage(context.packageName)
        intent.putExtra("URL", url)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        context.startActivity(intent)
    }

    fun navigateToRecipeList(context: Context, categoryId: String, categoryName: String) {
        val intent = Intent("feature.recipe.list")
        intent.setPackage(context.packageName)
        intent.putExtra("CATEGORY_ID", categoryId)
        intent.putExtra("CATEGORY_NAME", categoryName)
        context.startActivity(intent)
    }

    fun navigateToEditProfilePage(context: Context) {
        val intent = Intent("feature.edit.profile")
        intent.setPackage(context.packageName)
        context.startActivity(intent)
    }

}