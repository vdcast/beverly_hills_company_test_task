package com.example.beverly_hills_company_test_task.data.get

import android.content.Context
import android.provider.Settings

class AdbRepos {
    fun checkAdb(context: Context): Boolean {
        return try {
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED
            ) != 0
        } catch (e: Settings.SettingNotFoundException) {
            true
        }
    }
}