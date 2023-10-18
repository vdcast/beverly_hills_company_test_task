package com.example.beverly_hills_company_test_task.data.get

import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URLEncoder

class AppsFlyerRepos(private val context: Context) {
    companion object {
        const val DEV_KEY = "APPSFLYER_DEV_KEY"  /* TODO input your AppsFlyer dev key */
    }

    private val _appsFlyerData = MutableStateFlow<Map<String, String>?>(null)
    val appsFlyerData: StateFlow<Map<String, String>?> = _appsFlyerData.asStateFlow()

    private val _campaignState = MutableStateFlow<String?>(null)
    val campaignState: StateFlow<String?> = _campaignState.asStateFlow()

    private val _isAppsFlyerInited = MutableStateFlow(false)
    val isAppsFlyerInited: StateFlow<Boolean> = _isAppsFlyerInited.asStateFlow()

    private val conversionListener = object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
            val keysMap = mapOf(
                 "campaign" to "null"
            )
            val encodedDataMap = keysMap.map { (key, defaultValue) ->
                key to (data?.getOrElse(key) { defaultValue }?.toString()?.encode() ?: defaultValue.encode())
            }.toMap()
            encodedDataMap.forEach { (key, value) ->
                Log.d("MYLOG", "onConversionDataSuccess: $key - $value")
            }
            _appsFlyerData.tryEmit(encodedDataMap)
            val campaign = encodedDataMap["campaign"]?.encode()
            _campaignState.tryEmit(campaign)
            _isAppsFlyerInited.tryEmit(true)
        }
        override fun onConversionDataFail(error: String?) {
            _isAppsFlyerInited.tryEmit(true)
            Log.d("MYLOG", "onConversionDataFail: $error")
        }
        override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
            _appsFlyerData.tryEmit(null)
            data?.map {
                Log.d("MYLOG", "onAppOpenAttribution: ${it.key} = ${it.value}")
            }
        }
        override fun onAttributionFailure(error: String?) {
            _appsFlyerData.tryEmit(null)
            Log.d("MYLOG", "onAttributionFailure: $error")
        }
    }
    fun initAppsFlyer(
        updateLinksGetterStateAppsFlyerUID: (String) -> Unit
    ) {
        val appsFlyerLib = AppsFlyerLib.getInstance()
        appsFlyerLib.init(DEV_KEY, conversionListener, context)
        appsFlyerLib.start(context)
        val appsFlyerUID = appsFlyerLib.getAppsFlyerUID(context).toString()
        updateLinksGetterStateAppsFlyerUID(appsFlyerUID)
    }
    private fun String.encode(): String {
        return try {
            URLEncoder.encode(this, "utf-8")
        } catch (e: Exception) {
            "null"
        }
    }
}