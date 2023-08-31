package com.vti.messageapp.data.translators

import com.vti.messageapp.data.datasources.local.entities.ConversationEntity
import com.vti.messageapp.domain.entities.Conversation
import com.vti.messageapp.domain.entities.User

fun ConversationEntity.toEntity(participants: List<User>?): Conversation {
    return Conversation(
        id = id,
        title = title,
        ownerId = ownerId,
        participants = participants ?: listOf(),
    )
}