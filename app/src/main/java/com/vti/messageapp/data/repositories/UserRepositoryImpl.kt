package com.vti.messageapp.data.repositories

import com.vti.messageapp.data.datasources.local.sources.UserLocalDataSource
import com.vti.messageapp.data.translators.toEntity
import com.vti.messageapp.domain.entities.User
import com.vti.messageapp.domain.repositories.AuthRepository
import com.vti.messageapp.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {
    override fun getAllUsers(): Flow<List<User>> {
        return userLocalDataSource.getAllUsers().map { it.map { user -> user.toEntity() } }
    }

    override suspend fun getMyUserInfo(): User {
        val myPhone = authRepository.getAuthInfo()?.username ?: throw Exception("No username")
        return getUserByPhone(myPhone)
    }

    override suspend fun getUserById(id: Long): User {
        return userLocalDataSource.getUserById(id).toEntity()
    }

    override suspend fun getUserByPhone(phone: String): User {
        return userLocalDataSource.getUserByPhone(phone).toEntity()
    }
}