package com.food.core.auth.impl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.food.core.auth.BuildConfig
import com.food.core.auth.SocialMediaInterface
import com.food.core.auth.SocialMediaUserResult
import com.food.core.exception.SignInCancelException
import com.food.core.exception.UnknownProfileException
import com.food.core.utility.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson

/**
 * Created by mikekojansow on 02/08/20.
 * Senior Android Developer
 */
class GoogleImpl: SocialMediaInterface {

    companion object {
        private const val GOOGLE_SIGN_IN_REQUEST = 1403
        private const val GOOGLE_SIGN_IN_CANCEL = 12501
    }

    private val googleSignOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID).requestEmail().build()

    private var googleSignInClient: GoogleSignInClient? = null

    private var onSuccess: (String) -> Unit = {}
    private var onFailed: (Exception?) -> Unit = {}

    override fun doLogin(
        activity: AppCompatActivity,
        onSuccess: (String) -> Unit,
        onFailed: (Exception?) -> Unit
    ) {
        this.onSuccess = onSuccess
        this.onFailed = onFailed

        googleSignInClient = GoogleSignIn.getClient(activity, googleSignOption)
        val intent = googleSignInClient!!.signInIntent
        activity.startActivityForResult(intent, GOOGLE_SIGN_IN_REQUEST)
    }

    override fun doLogout(onSuccess: () -> Unit, onFailed: (Exception?) -> Unit) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)
            Log.printLog("Account Data", "${account?.idToken}")

            account?.let { account ->
                onSuccess(account.idToken ?: "")
            }
        } catch (ex: ApiException) {
            Log.printLog("Google Error", "Sign In dialog_error : ${ex.message}", ex)
            if (ex.statusCode == GOOGLE_SIGN_IN_CANCEL) {
                onFailed(SignInCancelException())
            } else {
                onFailed(ex)
            }
        }
    }
}