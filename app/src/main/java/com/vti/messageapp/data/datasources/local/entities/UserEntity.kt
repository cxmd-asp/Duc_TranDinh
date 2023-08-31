package com.vti.messageapp.data.datasources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vti.messageapp.domain.enums.UserStatus

@Entity(tableName = "users", indices = [Index(value = ["phone"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") val id: Long = 0,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "avatar") val avatar: String? = null,
    @ColumnInfo(name = "status") val status: UserStatus = UserStatus.OFFLINE,
)