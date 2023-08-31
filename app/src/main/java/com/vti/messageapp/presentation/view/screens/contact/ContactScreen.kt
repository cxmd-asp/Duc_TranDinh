package com.vti.messageapp.presentation.view.screens.contact

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.vti.messageapp.MainActivity
import com.vti.messageapp.domain.enums.UserStatus
import com.vti.messageapp.presentation.base.BasePage
import com.vti.messageapp.presentation.services.ChatService
import com.vti.messageapp.presentation.view.screens.destinations.ChatScreenDestination
import com.vti.messageapp.presentation.view.screens.destinations.ContactScreenDestination

@Destination
@Composable
fun ContactScreen(
    navigator: DestinationsNavigator,
    viewModel: ContactViewModel = hiltViewModel()
) {
    var myService: ChatService? by remember { mutableStateOf(null) }
    var serviceConnection: ServiceConnection? by remember { mutableStateOf(null) }

    val mainActivity = LocalContext.current as MainActivity

    LaunchedEffect(viewModel.conversation.value) {
        viewModel.conversation.value?.let {
            navigator.navigate(ChatScreenDestination(it.id)) {
                navigator.popBackStack(
                    ContactScreenDestination,
                    inclusive = true
                )
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
                        Text("Contact Screen", color = Color.White)
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                tint = Color.White,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                )
            },

            ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    items(viewModel.contacts) { item ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.getConversation(item)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier.size(60.dp),
                                shape = CircleShape
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .aspectRatio(1f)
                                )
                            }
                            Box(Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = item.phone,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                if (item.status == UserStatus.ONLINE) {
                                    Text(
                                        text = "Online",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Green
                                    )
                                } else {
                                    Text(
                                        text = "Offline",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Red
                                    )
                                }

                            }

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
    ContactScreen(EmptyDestinationsNavigator)
}