package com.vti.messageapp.data.datasources.local.app

import com.vti.messageapp.data.datasources.local.dao.UserDao
import com.vti.messageapp.data.datasources.local.entities.UserEntity
import com.vti.messageapp.data.datasources.local.sources.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {
    override fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAll()
    }

    override suspend fun getUserById(id: Long): UserEntity {
        return userDao.getById(id)
    }

    override suspend fun getUserByPhone(phone: String): UserEntity {
        return userDao.getByPhone(phone)
    }
}