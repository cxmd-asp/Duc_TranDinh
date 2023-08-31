package com.vti.messageapp.presentation.base

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vti.messageapp.presentation.view.widgets.LoadingDialog
import com.vti.messageapp.presentation.view.widgets.LoadingWidget

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BasePage(
    viewModel: BaseViewModel? = null,
    appBar: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable (BoxScope.() -> Unit)
) {
    val focusManager = LocalFocusManager.current

    viewModel?.let {
        if (viewModel.loading.value) {
            LaunchedEffect(viewModel.loading.value) {
                focusManager.clearFocus()
            }
        }
    }

    LaunchedEffect(Unit) {
        if (viewModel?.screenStatus?.value is ScreenStatus.Initial) {
            viewModel.loadData()
        }
    }

    Box(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        },
        contentAlignment = Alignment.Center
    ) {
        when (viewModel?.screenStatus?.value) {
            is ScreenStatus.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                )
                LoadingWidget()
            }
            is ScreenStatus.Loaded, null -> {
                Scaffold {
                    Column {
                        appBar?.let {
                            Box(content = appBar)
                        }
                        Box(content = content)
                        if (viewModel != null) {
                            if (viewModel.loading.value) {
                                LoadingDialog()
                            }
                        }
                    }
                }
            }
            is ScreenStatus.Error -> {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        appBar?.let {
                            Box(content = appBar)
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.height(36.dp))
                            Text(
                                "Error",
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(36.dp))
                        }
                    }
                }
            }
            else -> {
                Surface(modifier = Modifier.fillMaxSize()) { }
            }
        }
    }
}
