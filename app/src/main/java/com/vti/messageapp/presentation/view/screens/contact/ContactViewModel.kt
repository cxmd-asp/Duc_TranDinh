package com.vti.messageapp.presentation.view.screens.contact

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.vti.messageapp.domain.entities.Conversation
import com.vti.messageapp.domain.entities.User
import com.vti.messageapp.domain.repositories.AuthRepository
import com.vti.messageapp.domain.repositories.UserRepository
import com.vti.messageapp.domain.usecases.CreateConversationParams
import com.vti.messageapp.domain.usecases.GetConversationUseCase
import com.vti.messageapp.presentation.base.BaseViewModel
import com.vti.messageapp.presentation.base.ScreenStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val getConversationUseCase: GetConversationUseCase,
) : BaseViewModel() {
    private val _contacts = mutableStateListOf<User>()
    val contacts: SnapshotStateList<User> = _contacts

    private val _conversation = mutableStateOf<Conversation?>(null)
    val conversation: State<Conversation?> = _conversation

    override fun loadData() {
        setScreenStatus(ScreenStatus.Loading)
        viewModelScope.launch {
            val me = authRepository.getAuthInfo()
            launch {
                userRepository.getAllUsers().collect {
                    val userList = it.filter { item -> item.phone != me?.username }
                    _contacts.clear()
                    _contacts.addAll(userList)
                }
            }
            setScreenStatus(ScreenStatus.Loaded)
        }
    }

    fun getConversation(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val conversation = getConversationUseCase.invoke(CreateConversationParams(user))
            launch (Dispatchers.Main){
                _conversation.value = conversation
            }
        }
    }
}