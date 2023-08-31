package com.vti.messageapp.domain.usecases

import com.vti.messageapp.domain.repositories.ConversationRepository
import com.vti.messageapp.domain.repositories.UserRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val userRepository: UserRepository,
) : UseCase<Unit, SendMessageParam> {
    override suspend fun invoke(param: SendMessageParam) {
        val me = userRepository.getMyUserInfo()

        conversationRepository.sendMessage(param.message, me.id, param.toObj)
    }
}

data class SendMessageParam(
    val message: String,
    val toObj: Long
)