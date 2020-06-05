package com.mentarey.prototech.test.app.domain

import com.mentarey.prototech.test.app.data.pref.UserAccountRepository
import com.mentarey.prototech.test.app.data.retrofit.PrototechApi
import com.mentarey.prototech.test.app.data.retrofit.TokenInterceptor
import com.mentarey.prototech.test.app.data.retrofit.entity.UserCredentials
import com.mentarey.prototech.test.app.ui.state.UserAuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginInteractor(
    private val prototechApi: PrototechApi,
    private val tokenInterceptor: TokenInterceptor,
    private val userAccountRepository: UserAccountRepository
) {

    suspend fun authorizationWith(login: String, password: String): UserAuthState =
        withContext(Dispatchers.IO) {
            val userCredentials = UserCredentials(login, password)
            try {
                val token = prototechApi.getUserToken(userCredentials)
                userAccountRepository.setUserLogin(login)
                userAccountRepository.setUserToken(token)
                tokenInterceptor.authToken = token
                UserAuthState.Success
            } catch (e: Throwable) {
                UserAuthState.Error(e.localizedMessage)
            }
        }
}