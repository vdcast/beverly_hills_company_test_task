package com.example.beverly_hills_company_test_task.presentation.ui.screens

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beverly_hills_company_test_task.domain.AppViewModel

@Composable
fun WebView(
    appViewModel: AppViewModel = hiltViewModel(),
) {
    val urlToLoad by appViewModel.urlToLoad.collectAsState()
    WebViewScreen(url = urlToLoad, appViewModel = appViewModel)
    Log.d("MYLOG", "WebViewScreen urlToLoad: $urlToLoad")
}

@Composable
fun WebViewScreen(
    url: String,
    appViewModel: AppViewModel
) {

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                    loadUrl(url)

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.setSupportMultipleWindows(true)
                settings.apply {
                    javaScriptEnabled = true
                }
                isVerticalScrollBarEnabled = false
                isHorizontalScrollBarEnabled = false


                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        request?.let {
                            view?.loadUrl(it.url.toString())
                        }
                        return true
                    }
                }
            }
        },
        update = { view ->
            if (view.url != url) {
                view.loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}