package com.food.feature.webview

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import com.food.core.base.activity.BaseActivity
import kotlinx.android.synthetic.main.webview.*

/**
 * Created by mikekojansow on 08/10/20.
 * Senior Android Developer
 */
class WebViewActivity: BaseActivity(R.layout.webview) {

    companion object {
        const val TAG_URL = "URL"
    }

    private lateinit var presenter: WebViewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        presenter = WebViewPresenter(this)
        presenter.initiateWebview(intent.getStringExtra(TAG_URL))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()) {
            wv.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun setupViews() {
        setupToolbar()
        setupWebview()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupWebview() {
        val wvSettings = wv.settings
        wvSettings.javaScriptEnabled = true
        wvSettings.useWideViewPort = true

        wv.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        wv.webViewClient = NewWebViewClient(loading, onWebFinishedLoad = { title->
            setToolbarTitle(title)
        })
    }

    private fun setToolbarTitle(title: String) {
        toolbar.title = title
    }

    fun loadWebView(url: String) {
        wv.loadUrl(url)
    }
}