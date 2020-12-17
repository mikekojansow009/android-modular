package com.food.feature.webview

/**
 * Created by mikekojansow on 08/10/20.
 * Senior Android Developer
 */
class WebViewPresenter(private val activity: WebViewActivity) {

    fun initiateWebview(url: String?) {
        url?.let {
            activity.loadWebView(it)
        }
    }

}