package com.mentarey.prototech.test.app.data.pref

import android.content.SharedPreferences

internal class SettingsManagerImpl constructor(private val preferences: SharedPreferences) :
    SettingsManager {

    override suspend fun getString(key: String, defaultValue: String): String {
        return try {
            preferences.getString(key, defaultValue) ?: defaultValue
        } catch (e: ClassCastException) {
            defaultValue
        }
    }

    override suspend fun putString(key: String, value: String) {
        preferences.edit().apply {
            putString(key, value)
            apply()
        }
    }
}