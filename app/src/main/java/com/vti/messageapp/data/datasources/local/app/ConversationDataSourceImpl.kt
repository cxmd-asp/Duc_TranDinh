package com.vti.messageapp.data.datasources.local.app

import com.vti.messageapp.data.datasources.local.dao.ConversationDao
import com.vti.messageapp.data.datasources.local.dao.MessageDao
import com.vti.messageapp.data.datasources.local.dao.ParticipantDao
import com.vti.messageapp.data.datasources.local.entities.ConversationEntity
import com.vti.messageapp.data.datasources.local.entities.ConversationUserEntity
import com.vti.messageapp.data.datasources.local.entities.MessageEntity
import com.vti.messageapp.data.datasources.local.entities.ParticipantEntity
import com.vti.messageapp.data.datasources.local.sources.ConversationDataSource
import com.vti.messageapp.data.translators.toEntity
import com.vti.messageapp.domain.entities.Conversation
import com.vti.messageapp.domain.entities.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConversationDataSourceImpl @Inject constructor(
    private val conversationDao: ConversationDao,
    private val participantDao: ParticipantDao,
    private val messageDao: MessageDao
) : ConversationDataSource {
    override fun getConversationFlow(): Flow<List<ConversationUserEntity>> {
        return conversationDao.getAll()
    }

    override suspend fun sendMessage(message: String, fromObj: Long, toObj: Long) {
        val participant = participantDao.loadByUser(toObj) ?: throw Exception()
        messageDao.insertAll(
            MessageEntity(
                message = message,
                conversationID = participant.conversationId,
                senderID = fromObj
            )
        )
    }

    override suspend fun receiveMessage(message: String, fromObj: Long) {
        val participant = participantDao.loadByUser(fromObj) ?: throw Exception()

        messageDao.insertAll(
            MessageEntity(
                message = message,
                conversationID = participant.conversationId,
                senderID = fromObj
            )
        )
    }

    override suspend fun getConversationById(id: Long): Conversation? {
        val conversation = conversationDao.loadById(id)
        val participants = participantDao.getAllByConversation(id)
        return conversation.toEntity(participants?.map { it.toEntity() })
    }

    override suspend fun getConversationByUser(userId: Long): Conversation? {
        val conversation = conversationDao.loadByUser(userId)
        val participants = conversation?.id?.let { participantDao.getAllByConversation(it) }
        return conversation?.toEntity(participants?.map { it.toEntity() })
    }

    override suspend fun createConversation(
        title: String,
        ownerId: Long,
        participants: List<User>
    ): Conversation? {
        val conversationId =
            conversationDao.insertConversation(ConversationEntity(title = title, ownerId = ownerId))
        val conversation = conversationDao.loadById(conversationId)
        participantDao.insertAll(
            *participants.map {
                ParticipantEntity(participantId = it.id, conversationId = conversationId)
            }.toTypedArray()
        )
        return conversation.toEntity(participants)
    }
}