package com.vti.messageapp.data.repositories

import android.content.SharedPreferences
import com.vti.messageapp.domain.entities.AuthInfo
import com.vti.messageapp.domain.repositories.AuthRepository
import com.vti.messageapp.shared.Constants.Companion.PASSWORD
import com.vti.messageapp.shared.Constants.Companion.USER_NAME
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val sharedPreferences: dagger.Lazy<SharedPreferences>,
) : AuthRepository {
    override suspend fun login(username: String, password: String) {
        sharedPreferences.get().edit()
            .putString(USER_NAME, username)
            .putString(PASSWORD, password).apply()
    }

    override suspend fun logout() {
        sharedPreferences.get().edit().remove(USER_NAME).remove(PASSWORD).apply()
    }

    override suspend fun getAuthInfo(): AuthInfo? {
        val username = sharedPreferences.get().getString(USER_NAME, null) ?: ""
        val password = sharedPreferences.get().getString(PASSWORD, null) ?: ""
        if (username.isBlank() || password.isBlank()) {
            return null
        }

        return AuthInfo(username, password)
    }
}