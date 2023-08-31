package com.vti.messageapp.data.datasources.local.sources

import com.vti.messageapp.data.datasources.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun getAllUsers(): Flow<List<UserEntity>>

    suspend fun getUserById(id: Long): UserEntity

    suspend fun getUserByPhone(phone: String): UserEntity
}