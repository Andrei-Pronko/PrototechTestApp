package com.mentarey.prototech.test.app.data.pref

interface SettingsManager {
    suspend fun getString(key: String, defaultValue: String): String
    suspend fun putString(key: String, value: String)
}