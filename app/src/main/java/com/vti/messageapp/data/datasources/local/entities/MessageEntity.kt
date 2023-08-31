package com.vti.messageapp.data.datasources.local.entities

import androidx.room.*
import com.vti.messageapp.domain.enums.MessageStatus
import java.util.*

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = ConversationEntity::class,
            parentColumns = ["conversation_id"],
            childColumns = ["conversation_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["sender_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index(value = ["sender_id", "conversation_id"])]
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "date") val date: Long = Date().time,
    @ColumnInfo(name = "sender_id", index = true) val senderID: Long,
    @ColumnInfo(name = "conversation_id", index = true) val conversationID: Long,
    @ColumnInfo(name = "status") val status: MessageStatus = MessageStatus.PENDING,
)