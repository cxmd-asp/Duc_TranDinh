package com.vti.messageapp.presentation.view.screens.chat

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vti.messageapp.MainActivity
import com.vti.messageapp.domain.entities.MessageRegister
import com.vti.messageapp.presentation.base.BasePage
import com.vti.messageapp.presentation.services.ChatService
import com.vti.messageapp.presentation.view.screens.chat.components.ChatAppBar
import com.vti.messageapp.presentation.view.screens.chat.components.ChatInput
import com.vti.messageapp.presentation.view.screens.chat.components.ReceivedMessageRow
import com.vti.messageapp.presentation.view.screens.chat.components.SentMessageRow
import dagger.hilt.android.EntryPointAccessors
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun ChatScreen(
    navigator: DestinationsNavigator,
    conversationId: Long,
) {
    val viewModelFactory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).chatViewModelFactory()

    val viewModel: ChatViewModel = viewModel(
        factory = ChatViewModel.provideFactory(viewModelFactory, conversationId)
    )

    var myService: ChatService? by remember { mutableStateOf(null) }
    var serviceConnection: ServiceConnection? by remember { mutableStateOf(null) }

    val mainActivity = LocalContext.current as MainActivity

    LaunchedEffect(Unit) {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as ChatService.LocalBinder
                myService = binder.getService()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                myService = null
            }
        }

        val intent = Intent(mainActivity, ChatService::class.java)
        mainActivity.bindService(intent, serviceConnection!!, Context.BIND_AUTO_CREATE)
    }

    DisposableEffect(Unit) {
        onDispose {
            if (serviceConnection != null) {
                mainActivity.unbindService(serviceConnection!!)
            }
        }
    }

    BasePage(
        viewModel = viewModel
    ) {
        Scaffold(
            topBar = {
                ChatAppBar(
                    title = viewModel.conversation.value?.title ?: "",
                    description = "Offline",
                    onBackArrowClick = { navigator.navigateUp() })
            },
            bottomBar = {
                ChatInput(
                    onMessageChange = { messageContent ->
                        viewModel.conversation.value?.participants?.forEach { user ->
                            myService?.sendMessage(messageContent, user.id)
                        }
                    },
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
            ) {
                items(
                    viewModel.messages
                ) { message: MessageRegister ->
                    when (message.isMessageFromOpponent) {
                        true -> {
                            ReceivedMessageRow(
                                text = message.chatMessage.message
                            )
                        }

                        false -> {
                            SentMessageRow(
                                text = message.chatMessage.message
                            )
                        }
                    }
                }

            }
        }
    }
}