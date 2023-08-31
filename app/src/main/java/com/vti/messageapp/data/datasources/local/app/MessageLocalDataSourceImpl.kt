package com.vti.messageapp.data.datasources.local.app

import com.vti.messageapp.data.datasources.local.dao.MessageDao
import com.vti.messageapp.data.datasources.local.entities.MessageEntity
import com.vti.messageapp.data.datasources.local.sources.MessageLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageLocalDataSourceImpl @Inject constructor(
    private val messageDao: MessageDao
) : MessageLocalDataSource {
    override fun getMessageFlow(conversationId: Long): Flow<List<MessageEntity>> {
        return messageDao.getAllByConversation(conversationId)
    }
}