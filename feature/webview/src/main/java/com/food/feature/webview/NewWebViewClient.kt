package com.food.feature.webview

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Created by mikekojansow on 08/10/20.
 * Senior Android Developer
 */
class NewWebViewClient(private val loadingView: View, private var onWebFinishedLoad: (String) -> Unit): WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        loadingView.visibility = View.VISIBLE
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        loadingView.visibility = View.GONE

        onWebFinishedLoad(view?.title ?: "")
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url ?: "")
        return true
    }
}