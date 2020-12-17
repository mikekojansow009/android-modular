package com.food.core.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by mikekojansow on 01/08/20.
 * Senior Android Developer
 */
interface SocialMediaInterface {

    fun doLogin(activity: AppCompatActivity, onSuccess: (String) -> Unit, onFailed: (Exception?) -> Unit)

    fun doLogout(onSuccess: () -> Unit, onFailed: (Exception?) -> Unit)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

}