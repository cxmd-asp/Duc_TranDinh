package com.vti.messageapp.data.translators

import com.vti.messageapp.data.datasources.local.entities.MessageEntity
import com.vti.messageapp.domain.entities.ChatMessage
import com.vti.messageapp.domain.enums.MessageStatus
import java.util.*

fun MessageEntity.toEntity(): ChatMessage {
    return ChatMessage(
        message = message,
        date = Date(date),
        profileUUID = senderID,
        status = MessageStatus.RECEIVED
    )
}