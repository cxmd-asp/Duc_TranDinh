package com.vti.messageapp.data.datasources.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserConversationEntity(
    @Embedded val participants: UserEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "conversation_id",
        associateBy = Junction(ParticipantEntity::class)
    )
    val conversation: ConversationEntity,
)