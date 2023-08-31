package com.vti.messageapp.presentation.resources

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme
    CompositionLocalProvider(
        LocalAppColors provides Colors,
        LocalAppTypography provides Typography,
        LocalAppShape provides Shapes,
        content = {
            ProvideTextStyle(
                value = Typography.mainText.copy(color = Colors.mainText),
                content = content
            )
        }
    )
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current
    val shapes: AppShape
        @Composable
        get() = LocalAppShape.current
}
