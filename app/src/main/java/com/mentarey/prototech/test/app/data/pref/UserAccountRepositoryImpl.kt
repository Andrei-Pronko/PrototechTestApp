package com.mentarey.prototech.test.app.data.pref

class UserAccountRepositoryImpl(private val settingsManager: SettingsManager) :
    UserAccountRepository {

    override suspend fun setUserLogin(login: String) {
        settingsManager.putString(userLoginKey, login)
    }

    override suspend fun getUserLogin(): String {
        return settingsManager.getString(userLoginKey, EMPTY_LINE)
    }

    override suspend fun setUserToken(token: String) {
        settingsManager.putString(userTokenKey, token)
    }

    override suspend fun getUserToken(): String {
        return settingsManager.getString(userTokenKey, EMPTY_LINE)
    }

    companion object {
        private const val EMPTY_LINE = ""
        private const val userLoginKey = "userLoginKey"
        private const val userTokenKey = "userTokenKey"
    }
}