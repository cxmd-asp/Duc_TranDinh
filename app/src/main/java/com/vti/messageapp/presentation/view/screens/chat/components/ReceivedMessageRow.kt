package com.vti.messageapp.presentation.view.screens.chat.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReceivedMessageRow(text: String) {
    Column(
        horizontalAlignment = Alignment.Start, modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = 8.dp,
                end = 64.dp,
                top = 4.dp,
                bottom = 4.dp
            )
    ) {
        Text(text)

    }
}