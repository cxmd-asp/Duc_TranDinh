package com.vti.messageapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.vti.messageapp.domain.repositories.AuthRepository
import com.vti.messageapp.presentation.base.BaseViewModel
import com.vti.messageapp.presentation.base.ScreenStatus
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private  val authRepository: AuthRepository
) : BaseViewModel() {
    private val _isAuthenticated = mutableStateOf(false)
    val isAuthenticated: State<Boolean> = _isAuthenticated

    override fun loadData() {
        setScreenStatus(ScreenStatus.Loading)
        viewModelScope.launch {
            val authInfo = authRepository.getAuthInfo()
            _isAuthenticated.value = authInfo != null
            setScreenStatus(ScreenStatus.Loaded)
        }
    }
}