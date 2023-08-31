package com.vti.messageapp.data.repositories

import com.vti.messageapp.data.datasources.local.sources.ConversationDataSource
import com.vti.messageapp.data.translators.toEntity
import com.vti.messageapp.domain.entities.Conversation
import com.vti.messageapp.domain.entities.MessageRegister
import com.vti.messageapp.domain.entities.User
import com.vti.messageapp.domain.repositories.ConversationRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationRepositoryImpl @Inject constructor(
    private val conversationDataSource: ConversationDataSource
) : ConversationRepository {

    override fun getConversationFlow() = conversationDataSource.getConversationFlow().map {
        it.map { conversation ->
            Conversation(
                id = conversation.conversation.id,
                title = conversation.conversation.title,
                ownerId = conversation.conversation.ownerId,
                participants = listOf(),
                lastMessage = conversation.lastMessage?.let { lastMessage ->
                    MessageRegister(
                        chatMessage = lastMessage.toEntity(),
                        isMessageFromOpponent = true
                    )
                }
            )
        }
    }

    override suspend fun sendMessage(message: String, fromObj: Long, toObj: Long) {
        conversationDataSource.sendMessage(message, fromObj, toObj)
    }

    override suspend fun receiveMessage(message: String, fromObj: Long) {
        conversationDataSource.receiveMessage(message, fromObj)
    }

    override suspend fun getConversationById(id: Long): Conversation? {
        return conversationDataSource.getConversationById(id)
    }

    override suspend fun getConversationByUser(userId: Long): Conversation? {
        return conversationDataSource.getConversationByUser(userId)
    }

    override suspend fun createConversation(
        title: String,
        ownerId: Long,
        participants: List<User>
    ): Conversation? {
        return conversationDataSource.createConversation(title, ownerId, participants)
    }
}