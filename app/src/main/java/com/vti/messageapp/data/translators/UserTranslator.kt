package com.vti.messageapp.data.translators

import com.vti.messageapp.data.datasources.local.entities.UserEntity
import com.vti.messageapp.domain.entities.User

fun UserEntity.toEntity(): User {
    return User(
        id = id,
        name = name ?: "",
        phone = phone,
        avatar = avatar ?: "",
        status = status
    )
}