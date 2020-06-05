package com.mentarey.prototech.test.app.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentarey.prototech.test.app.domain.LoginInteractor
import com.mentarey.prototech.test.app.ext.Event
import com.mentarey.prototech.test.app.ui.state.UserAuthState
import kotlinx.coroutines.launch

class LoginViewModel(private val loginInteractor: LoginInteractor) : ViewModel() {

    private val _userAuthState = MutableLiveData<Event<UserAuthState>>()
    val userAuthState: LiveData<Event<UserAuthState>> = _userAuthState

    private val _loadingProgress = MutableLiveData<Boolean>()
    val loadingProgress: LiveData<Boolean> = _loadingProgress

    fun authorizationWith(login: String, password: String) {
        viewModelScope.launch {
            _loadingProgress.value = true
            val userAuthState = loginInteractor.authorizationWith(login, password)
            _userAuthState.value = Event(userAuthState)
            _loadingProgress.value = false
        }
    }
}