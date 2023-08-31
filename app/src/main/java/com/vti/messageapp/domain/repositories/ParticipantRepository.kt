package com.vti.messageapp.domain.repositories

import com.vti.messageapp.domain.entities.User

interface ParticipantRepository {

    fun loadByUser(userId: Long): User?
}