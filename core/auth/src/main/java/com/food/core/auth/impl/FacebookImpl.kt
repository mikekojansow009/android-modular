package com.food.core.auth.impl

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.food.core.auth.SocialMediaInterface
import com.food.core.auth.SocialMediaUserResult
import com.food.core.utility.Log
import java.util.*

/**
 * Created by mikekojansow on 01/08/20.
 * Senior Android Developer
 */
class FacebookImpl: SocialMediaInterface {
    private var loginManager: LoginManager? = null
    private var callbackManager: CallbackManager? = null

    init {
        loginManager = LoginManager.getInstance()
        callbackManager = CallbackManager.Factory.create()
    }

    override fun doLogin(activity: AppCompatActivity, onSuccess: (String) -> Unit, onFailed: (Exception?) -> Unit) {
        loginManager?.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {

                onSuccess(result.accessToken.token)
            }

            override fun onCancel() {
                onFailed(null)
            }

            override fun onError(error: FacebookException?) {
                Log.printLog("Error Login Facebook", error?.message ?: "", error)
                onFailed(error)
            }
        })

        loginManager?.logInWithReadPermissions(activity, listOf("public_profile", "email"))
    }

    override fun doLogout(onSuccess: () -> Unit, onFailed: (Exception?) -> Unit) {
        if (AccessToken.getCurrentAccessToken() == null) {
            onSuccess()
            return
        }

        GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, GraphRequest.Callback {
            loginManager?.logOut()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}