package com.mentarey.prototech.test.app.data.pref

interface UserAccountRepository {
    suspend fun setUserLogin(login: String)
    suspend fun getUserLogin(): String

    suspend fun setUserToken(token: String)
    suspend fun getUserToken(): String
}