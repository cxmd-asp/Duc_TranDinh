package com.vti.messageapp.presentation.view.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.vti.messageapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {
    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    fun changeUsername(username: String) {
        _username.value = username
    }

    fun changePassword(password: String) {
        _password.value = password
    }
}