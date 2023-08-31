package com.vti.messageapp.presentation.view.screens.chatlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.vti.messageapp.domain.entities.Conversation
import com.vti.messageapp.domain.repositories.AuthRepository
import com.vti.messageapp.domain.repositories.ConversationRepository
import com.vti.messageapp.domain.usecases.LogoutUseCase
import com.vti.messageapp.domain.usecases.NoParam
import com.vti.messageapp.presentation.base.BaseViewModel
import com.vti.messageapp.presentation.base.ScreenStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val authRepository: AuthRepository,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel() {
    private val _me = mutableStateOf<String?>(null)
    val me: State<String?> = _me

    private val _conversation = mutableStateListOf<Conversation>()
    val conversations: SnapshotStateList<Conversation> = _conversation

    private val _logoutSuccess = mutableStateOf<Boolean>(false)
    val logoutSuccess: State<Boolean> = _logoutSuccess

    override fun loadData() {
        setScreenStatus(ScreenStatus.Loading)
        viewModelScope.launch {
            _me.value = authRepository.getAuthInfo()?.username
            launch {
                conversationRepository.getConversationFlow().collect {
                    _conversation.clear()
                    _conversation.addAll(it.sortedByDescending { conversation -> conversation.lastMessage?.chatMessage?.date })
                }
            }
            setScreenStatus(ScreenStatus.Loaded)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke(NoParam())
            _logoutSuccess.value = true
        }
    }
}