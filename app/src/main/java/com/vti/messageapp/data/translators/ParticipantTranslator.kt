package com.vti.messageapp.data.translators

import com.vti.messageapp.data.datasources.local.entities.ParticipantEntity
import com.vti.messageapp.domain.entities.User

fun ParticipantEntity.toEntity(): User {
    return User(
        id = participantId,
    )
}