package com.vti.messageapp.data.repositories

import com.vti.messageapp.data.datasources.local.sources.MessageLocalDataSource
import com.vti.messageapp.data.translators.toEntity
import com.vti.messageapp.domain.entities.ChatMessage
import com.vti.messageapp.domain.repositories.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private  val messageLocalDataSource: MessageLocalDataSource
) : MessageRepository {
    override fun getMessageFlow(conversationId: Long): Flow<List<ChatMessage>> {
        return messageLocalDataSource.getMessageFlow(conversationId).map { it.map { message -> message.toEntity() } }
    }
}