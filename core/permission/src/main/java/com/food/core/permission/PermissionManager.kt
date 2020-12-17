package com.food.core.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import javax.inject.Inject

/**
 * Created by mikekojansow on 29/07/20.
 * Senior Android Developer
 */
typealias OnPermissionGranted = (Boolean) -> Unit

class PermissionManager @Inject constructor(private val activity: FragmentActivity) {

    companion object {
        private const val PERMISSION_READ_PHONE_STATE = 3201
    }

    private val permissionContentMap = HashMap<Int, OnPermissionGranted>()

    fun onRequestPermissionCallback(requestCode: Int,
                             permissions: Array<out String>,
                             grantResults: IntArray) {
        if(grantResults.isNotEmpty()) {
            var isAllGranted = true

            for (grantResult in grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    isAllGranted = false
                }
            }

            val permissionResponse = permissionContentMap[requestCode]
            permissionResponse?.invoke(isAllGranted)
        }
    }

    fun shouldShowReadPhonePermission(onPermissionGranted: OnPermissionGranted) {
        if (isReadPhoneGranted(activity)) return onPermissionGranted(true)

        permissionContentMap[PERMISSION_READ_PHONE_STATE] = onPermissionGranted

        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_PRECISE_PHONE_STATE), PERMISSION_READ_PHONE_STATE)
    }

    private fun isReadPhoneGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
    }

}