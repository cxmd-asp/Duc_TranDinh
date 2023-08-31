package com.vti.messageapp.data.datasources.local.converters

import androidx.room.TypeConverter
import com.vti.messageapp.domain.enums.UserStatus

class UserStatusConverter {
    @TypeConverter
    fun fromUserStatus(value: UserStatus): Boolean = value == UserStatus.ONLINE

    @TypeConverter
    fun toUserStatus(value: Boolean) = if (value) UserStatus.ONLINE else UserStatus.OFFLINE
}