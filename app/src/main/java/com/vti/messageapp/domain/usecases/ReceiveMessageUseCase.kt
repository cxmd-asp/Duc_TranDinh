package com.vti.messageapp.domain.usecases

import com.vti.messageapp.domain.repositories.ConversationRepository
import javax.inject.Inject

class ReceiveMessageUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository,
) : UseCase<Unit, ReceiveMessageParam> {
    override suspend fun invoke(param: ReceiveMessageParam) {
        conversationRepository.receiveMessage(param.message, param.fromObj)
    }
}

data class ReceiveMessageParam(
    val message: String,
    val fromObj: Long
)