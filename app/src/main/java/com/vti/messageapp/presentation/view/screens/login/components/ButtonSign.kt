package com.vti.messageapp.presentation.view.screens.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonSign(
    onclick: () -> Unit,
    signInOrSignUp: String
) {
    Button(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        onClick = {
            onclick()
        })
    {
        Text(
            text = signInOrSignUp,
            color = Color.White,
        )
    }
}