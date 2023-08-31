package com.vti.messageapp

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import com.vti.messageapp.presentation.resources.AppTheme
import com.vti.messageapp.presentation.services.ChatService
import com.vti.messageapp.presentation.view.screens.NavGraphs
import com.vti.messageapp.presentation.view.screens.chat.ChatViewModel
import com.vti.messageapp.presentation.view.screens.destinations.ChatListScreenDestination
import com.vti.messageapp.presentation.view.screens.destinations.LoginScreenDestination
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var mainViewModel: MainViewModel

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun chatViewModelFactory(): ChatViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mainViewModel.loadData()
        val intent = Intent(this, ChatService::class.java).apply {
            putExtra("isAuthenticated", mainViewModel.isAuthenticated.value)
        }
        startService(intent)

        setContent {
            AppTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    startRoute = if (mainViewModel.isAuthenticated.value) ChatListScreenDestination else LoginScreenDestination
                )
            }
        }
    }
}