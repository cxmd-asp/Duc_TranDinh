package com.vti.messageapp.data.datasources.local.sources

import com.vti.messageapp.data.datasources.local.entities.ConversationUserEntity
import com.vti.messageapp.domain.entities.Conversation
import com.vti.messageapp.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface ConversationDataSource {
    fun getConversationFlow(): Flow<List<ConversationUserEntity>>

    suspend fun sendMessage(message: String, fromObj: Long, toObj: Long)

    suspend fun receiveMessage(message: String, fromObj: Long)

    suspend fun getConversationById(id: Long): Conversation?

    suspend fun getConversationByUser(userId: Long): Conversation?

    suspend fun createConversation(title: String, ownerId: Long, participants: List<User>): Conversation?
}