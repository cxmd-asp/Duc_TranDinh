package com.vti.messageapp.domain.entities

import com.vti.messageapp.domain.enums.UserStatus

data class User (
    val id: Long,
    val name: String = "",
    val phone: String = "",
    val avatar: String = "",
    var status: UserStatus = UserStatus.OFFLINE,
)