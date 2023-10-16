package com.example.beverly_hills_company_test_task.data

import android.content.Context
import javax.inject.Inject

class SharedPrefs @Inject constructor(context: Context) {
    private val SAVED_LINK = "saved_link"

    private val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    fun setSavedLink(savedLink: String) { prefs.edit().putString(SAVED_LINK, savedLink).apply() }
    fun getSavedLink(): String = prefs.getString(SAVED_LINK, "") ?: ""
}