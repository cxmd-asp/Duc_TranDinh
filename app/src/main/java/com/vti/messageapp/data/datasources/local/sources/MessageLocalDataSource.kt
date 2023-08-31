package com.vti.messageapp.data.datasources.local.sources

import com.vti.messageapp.data.datasources.local.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

interface MessageLocalDataSource {
    fun getMessageFlow(conversationId: Long) : Flow<List<MessageEntity>>
}