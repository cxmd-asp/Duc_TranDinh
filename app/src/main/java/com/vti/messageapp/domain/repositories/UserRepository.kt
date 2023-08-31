package com.vti.messageapp.domain.repositories

import com.vti.messageapp.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(): Flow<List<User>>

    suspend fun getMyUserInfo() : User

    suspend fun getUserById(id: Long): User

    suspend fun getUserByPhone(phone: String): User
}