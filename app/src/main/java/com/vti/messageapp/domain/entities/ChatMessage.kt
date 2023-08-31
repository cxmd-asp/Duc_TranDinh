package com.vti.messageapp.domain.entities

import com.vti.messageapp.domain.enums.MessageStatus
import java.util.*

data class ChatMessage(
    val profileUUID: Long,
    var message: String = "",
    var date: Date = Date(),
    var status: MessageStatus = MessageStatus.PENDING
)
