package com.vti.messageapp.domain.repositories

import com.vti.messageapp.domain.entities.ChatMessage
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessageFlow(conversationId: Long) : Flow<List<ChatMessage>>
}