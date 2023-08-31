package com.vti.messageapp.presentation.view.screens.login

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vti.messageapp.MainActivity
import com.vti.messageapp.data.datasources.local.AppDatabase
import com.vti.messageapp.presentation.base.BasePage
import com.vti.messageapp.presentation.services.ChatService
import com.vti.messageapp.presentation.view.screens.destinations.ChatListScreenDestination
import com.vti.messageapp.presentation.view.screens.destinations.LoginScreenDestination
import com.vti.messageapp.presentation.view.screens.login.components.ButtonSign
import com.vti.messageapp.presentation.view.widgets.AppTextField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
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

    LaunchedEffect(myService?.loading?.value) {
        if (myService?.loading?.value == true) {
            viewModel.showLoading()
        } else {
            viewModel.hideLoading()
        }
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(myService?.loginSuccess?.value) {
        if (myService?.loginSuccess?.value == true) {
            myService?.loginSuccess?.value = false
            viewModel.hideLoading()
            scope.launch(Dispatchers.IO) {
                AppDatabase.clearDatabase(mainActivity)
            }
            navigator.navigate(ChatListScreenDestination) {
                popUpTo(LoginScreenDestination.route) {inclusive = true}
            }
        }
    }

    BasePage(
        viewModel = viewModel
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("Login", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))
            Box(modifier = Modifier.padding(top = 20.dp)) {
                AppTextField(modifier = Modifier
                    .height(44.dp)
                    .fillMaxWidth(),
                    value = viewModel.username.value,
                    placeHolder = "Username",
                    onTextChange = { name ->
                        viewModel.changeUsername(name)
                    }
                )
            }

            Box(modifier = Modifier.padding(top = 8.dp)) {
                AppTextField(modifier = Modifier
                    .height(44.dp)
                    .fillMaxWidth(),
                    value = viewModel.password.value,
                    placeHolder = "Password",
                    onTextChange = { password -> viewModel.changePassword(password) }
                )

            }
            Spacer(Modifier.height(10.dp))

            ButtonSign(
                onclick = {
                    myService?.login(viewModel.username.value, viewModel.password.value)
                },
                signInOrSignUp = "Sign In"
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}
