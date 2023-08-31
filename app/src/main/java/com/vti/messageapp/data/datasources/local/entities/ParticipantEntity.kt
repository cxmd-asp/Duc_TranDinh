package com.vti.messageapp.data.datasources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "participants",
    primaryKeys = ["user_id", "conversation_id"],
    indices = [Index(value = ["user_id", "conversation_id"])]
)
data class ParticipantEntity(
    @ColumnInfo(name = "user_id", index = true) val participantId: Long,
    @ColumnInfo(name = "conversation_id", index = true) val conversationId: Long,
)