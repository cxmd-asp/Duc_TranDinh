package com.vti.messageapp.data.datasources.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ConversationUserEntity(
    @Embedded val conversation: ConversationEntity,
    @Relation(
        parentColumn = "conversation_id",
        entityColumn = "conversation_id",
        associateBy = Junction(MessageEntity::class)
    )
    val lastMessage: MessageEntity?
)