package com.vti.messageapp.presentation.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val _screenStatus = mutableStateOf<ScreenStatus>(ScreenStatus.Initial)
    val screenStatus: State<ScreenStatus> = _screenStatus

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    open fun loadData() {
        setScreenStatus(ScreenStatus.Loaded)
    }

    fun showLoading() {
        _loading.value = true
    }

    fun hideLoading() {
        _loading.value = false
    }

    protected fun setScreenStatus(screenStatus: ScreenStatus) {
        _screenStatus.value = screenStatus
    }

    open fun retry() {
        loadData()
    }
}
