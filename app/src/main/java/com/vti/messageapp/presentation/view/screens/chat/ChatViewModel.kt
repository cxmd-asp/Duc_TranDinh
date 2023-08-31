package com.vti.messageapp.presentation.view.screens.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vti.messageapp.domain.entities.Conversation
import com.vti.messageapp.domain.entities.MessageRegister
import com.vti.messageapp.domain.repositories.ConversationRepository
import com.vti.messageapp.domain.repositories.MessageRepository
import com.vti.messageapp.domain.repositories.UserRepository
import com.vti.messageapp.presentation.base.BaseViewModel
import com.vti.messageapp.presentation.base.ScreenStatus
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel @AssistedInject constructor(
    private val userRepository: UserRepository,
    private  val messageRepository: MessageRepository,
    private val conversationRepository: ConversationRepository,
    @Assisted private val conversationId: Long
) : BaseViewModel() {
    private val _conversation = mutableStateOf<Conversation?>(null)
    val conversation: State<Conversation?> = _conversation

    private val _messages = mutableStateListOf<MessageRegister>()
    val messages: SnapshotStateList<MessageRegister> = _messages

    @AssistedFactory
    interface Factory {
        fun create(userId: Long): ChatViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            userId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(userId) as T
            }
        }
    }

    override fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            setScreenStatus(ScreenStatus.Loading)
            val me = userRepository.getMyUserInfo()

            val conversation = conversationRepository.getConversationById(conversationId)
            launch {
                _conversation.value = conversation
            }

            launch {
                conversation?.id?.let { conversationId ->
                    messageRepository.getMessageFlow(conversationId).collect {
                        _messages.clear()
                        _messages.addAll(it.map { message ->
                            MessageRegister(
                                message,
                                message.profileUUID != me.id
                            )
                        })
                    }
                }
            }
            setScreenStatus(ScreenStatus.Loaded)
        }
    }
}