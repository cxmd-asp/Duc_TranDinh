package com.vti.messageapp.data.datasources.local.converters

import androidx.room.TypeConverter
import com.vti.messageapp.domain.enums.MessageStatus

class MessageStatusConverters {
    @TypeConverter
    fun fromMessageStatus(value: MessageStatus): String = value.toString()

    @TypeConverter
    fun toMessageStatus(value: String) = MessageStatus.valueOf(value)
}