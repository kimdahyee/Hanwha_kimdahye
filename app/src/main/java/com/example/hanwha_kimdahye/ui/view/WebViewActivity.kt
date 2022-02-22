package com.example.hanwha_kimdahye.ui.view

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private lateinit var section: String
    private lateinit var webUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWebInfo()
        init()
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
            return
        }
        super.onBackPressed()
    }

    private fun init() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            applyWebViewSettings()
            if (section == "ir") {
                loadUrl("http://docs.google.com/viewer?url=$webUrl")
            } else {
                webUrl?.let { loadUrl(it) }
            }
        }
    }

    private fun applyWebViewSettings() {
        binding.webView.settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(false)
            builtInZoomControls = false
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            cacheMode = WebSettings.LOAD_NO_CACHE
            domStorageEnabled = true
        }
    }

    private fun getWebInfo() {
        section = intent.getStringExtra("section").toString()
        webUrl = intent.getStringExtra("webUrl").toString()
    }
}
