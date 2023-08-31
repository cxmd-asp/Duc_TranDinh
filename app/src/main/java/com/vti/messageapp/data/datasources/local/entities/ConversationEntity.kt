package com.vti.messageapp.data.datasources.local.entities

import androidx.room.*

@Entity(
    tableName = "conversations",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["user_id"],
        childColumns = ["owner_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["owner_id"])]
)
data class ConversationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "conversation_id") val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "owner_id") val ownerId: Long,
)