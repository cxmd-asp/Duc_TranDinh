package com.vti.messageapp.presentation.view.screens.chatlist

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.vti.messageapp.MainActivity
import com.vti.messageapp.presentation.base.BasePage
import com.vti.messageapp.presentation.services.ChatService
import com.vti.messageapp.presentation.view.screens.chatlist.components.AcceptPendingRequestList
import com.vti.messageapp.presentation.view.screens.destinations.ChatScreenDestination
import com.vti.messageapp.presentation.view.screens.destinations.ContactScreenDestination
import com.vti.messageapp.presentation.view.screens.destinations.LoginScreenDestination

@Destination
@Composable
fun ChatListScreen(
    navigator: DestinationsNavigator,
    viewModel: ChatListViewModel = hiltViewModel()
) {
    var myService: ChatService? by remember { mutableStateOf(null) }
    var serviceConnection: ServiceConnection? by remember { mutableStateOf(null) }

    val mainActivity = LocalContext.current as MainActivity

    var mDisplayMenu by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.logoutSuccess.value) {
        if (viewModel.logoutSuccess.value) {
            myService?.logout()
            navigator.navigate(LoginScreenDestination) {
                popUpTo(ChatScreenDestination.route) {inclusive = true}
            }
        }
    }

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
                TopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    title = {
                        Text("Chat List Screen", color = Color.White)
                    },
                    actions = {
                        IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                            Icon(Icons.Default.MoreVert, "")
                        }
                        DropdownMenu(
                            expanded = mDisplayMenu,
                            onDismissRequest = { mDisplayMenu = false }
                        ) {
                            DropdownMenuItem(onClick = { }) {
                                Text(text = "Me: ${viewModel.me.value}")
                            }
                            DropdownMenuItem(onClick = { viewModel.logout() }) {
                                Text(text = "Logout")
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = Color.Blue,
                    onClick = {
                        navigator.navigate(ContactScreenDestination)
                    }
                ) {
                    Icon(Icons.Filled.Message,"", tint = Color.White)
                }
            }
        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    items(viewModel.conversations) { item ->
                        AcceptPendingRequestList(item) {
                            navigator.navigate(ChatScreenDestination(item.id))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ContactPreview() {
    ChatListScreen(EmptyDestinationsNavigator)
}