package com.vti.messageapp.domain.usecases

import com.vti.messageapp.domain.entities.Conversation
import com.vti.messageapp.domain.entities.User
import com.vti.messageapp.domain.repositories.ConversationRepository
import com.vti.messageapp.domain.repositories.UserRepository
import javax.inject.Inject

class GetConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val userRepository: UserRepository,

) : UseCase<Conversation, CreateConversationParams> {
    override suspend fun invoke(param: CreateConversationParams): Conversation {
        val me = userRepository.getMyUserInfo()
        var conversation = conversationRepository.getConversationByUser(param.user.id)
        if (conversation == null) {
            conversation = conversationRepository.createConversation(
                title = param.user.phone,
                ownerId = me.id,
                participants = listOf(me, param.user)
            )
        }

        return conversation!!
    }
}

data class CreateConversationParams (
    val user: User
)