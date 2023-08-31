package com.vti.messageapp.presentation.base

sealed class ScreenStatus {
    object Initial : ScreenStatus()
    object Loading : ScreenStatus()
    object Loaded : ScreenStatus()
    object Error : ScreenStatus()
}
