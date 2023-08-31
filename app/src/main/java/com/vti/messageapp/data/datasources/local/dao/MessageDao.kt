package com.vti.messageapp.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vti.messageapp.data.datasources.local.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE messages.conversation_id = :conversationId")
    fun getAllByConversation(conversationId: Long): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE id IN (:id)")
    fun loadAllByIds(id: IntArray): List<MessageEntity>

    @Insert
    fun insertAll(vararg messages: MessageEntity)

    @Delete
    fun delete(message: MessageEntity)
}