package com.example.beverly_hills_company_test_task.data.get

import android.content.Context
import android.util.Log
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FacebookRepos {
    private val _deeplinkState = MutableStateFlow<String?>(null)
    val deepLinkState: StateFlow<String?> = _deeplinkState.asStateFlow()

    private val _isFacebookInited = MutableStateFlow(false)
    val isFacebookInited: StateFlow<Boolean> = _isFacebookInited.asStateFlow()

    private val facebookAppId = "FB_APP_ID" /* TODO input your FB app id */
    private val facebookToken = "FB_TOKEN" /* TODO input your FB token */

    fun initFacebook(context: Context) {
        var deeplink: String?
        FacebookSdk.apply {
            setApplicationId(facebookAppId)
            setClientToken(facebookToken)

            sdkInitialize(context)
            setAdvertiserIDCollectionEnabled(true)
            setAutoInitEnabled(true)
            fullyInitialize()
        }
        AppLinkData.fetchDeferredAppLinkData(context) {
            deeplink = it?.targetUri?.toString()
            deeplink?.let {
                _deeplinkState.value = deeplink
            }
        }
        _isFacebookInited.value = true
    }
}