package com.example.beverly_hills_company_test_task.domain

import androidx.lifecycle.ViewModel
import com.example.beverly_hills_company_test_task.data.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val prefs: SharedPrefs
) : ViewModel() {
    private val _urlToLoad = MutableStateFlow(prefs.getSavedLink())
    val urlToLoad: StateFlow<String> = _urlToLoad.asStateFlow()

    val domain = "https://divvyuqduv.online/PtyQ47"

    fun setDefaultUrl() {
        prefs.setSavedLink("https://google.com")
    }

    fun updatePage() {
        _urlToLoad.value = prefs.getSavedLink()
    }
}