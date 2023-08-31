package com.vti.messageapp.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vti.messageapp.data.datasources.local.entities.ParticipantEntity
import com.vti.messageapp.data.datasources.local.entities.UserEntity

@Dao
interface ParticipantDao {
    @Query("SELECT * FROM participants")
    fun getAll(): List<ParticipantEntity>

    @Query("SELECT * FROM participants, users WHERE users.user_id = participants.user_id AND participants.conversation_id = :conversationId")
    fun getAllByConversation(conversationId: Long): List<UserEntity>?

    @Query("SELECT * FROM participants WHERE user_id = :participantId")
    fun loadByUser(participantId: Long): ParticipantEntity?

    @Insert
    fun insertAll(vararg participants: ParticipantEntity)

    @Delete
    fun delete(participant: ParticipantEntity)
}