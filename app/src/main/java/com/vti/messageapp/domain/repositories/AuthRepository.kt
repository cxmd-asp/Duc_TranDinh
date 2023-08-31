package com.vti.messageapp.domain.repositories

import com.vti.messageapp.domain.entities.AuthInfo

interface AuthRepository {
    suspend fun login(username: String, password: String)

    suspend fun logout()

    suspend fun getAuthInfo(): AuthInfo?
}