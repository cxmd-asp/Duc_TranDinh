package com.vti.messageapp.data.datasources.local.dao

import androidx.room.*
import com.vti.messageapp.data.datasources.local.entities.ConversationEntity
import com.vti.messageapp.data.datasources.local.entities.ConversationUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {
    @Transaction
    @Query("SELECT conversations.*, messages.id, messages.message, messages.date, messages.sender_id, messages.status FROM conversations LEFT JOIN messages ON messages.conversation_id = conversations.conversation_id AND messages.date = (SELECT MAX(messages.date) FROM messages) ORDER BY messages.date DESC")
    fun getAll(): Flow<List<ConversationUserEntity>>

    @Query("SELECT * FROM conversations WHERE conversation_id = :id")
    suspend fun loadById(id: Long): ConversationEntity

    @Query("SELECT * FROM conversations, participants, users WHERE conversations.conversation_id = participants.conversation_id AND participants.user_id = users.user_id AND users.user_id = :userId")
    suspend fun loadByUser(userId: Long): ConversationEntity?

    @Insert
    fun insertAll(vararg conversations: ConversationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConversation(conversations: ConversationEntity): Long

    @Delete
    fun delete(conversation: ConversationEntity)
}