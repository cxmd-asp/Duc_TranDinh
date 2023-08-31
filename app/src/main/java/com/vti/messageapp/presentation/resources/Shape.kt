package com.vti.messageapp.presentation.resources

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppShape(
    val mini: Dp,
    val small: Dp,
    val medium: Dp,
    val big: Dp,
    val large: Dp,
    val largest: Dp,
    val wide: Dp,
    val huge: Dp
)

val LocalAppShape = staticCompositionLocalOf {
    AppShape(
        mini = Dp.Unspecified,
        small = Dp.Unspecified,
        medium = Dp.Unspecified,
        big = Dp.Unspecified,
        large = Dp.Unspecified,
        largest = Dp.Unspecified,
        wide = Dp.Unspecified,
        huge = Dp.Unspecified
    )
}

val Shapes = AppShape(
    mini = 2.dp,
    small = 3.dp,
    medium = 9.dp,
    big = 12.dp,
    large = 18.dp,
    largest = 20.dp,
    wide = 22.dp,
    huge = 24.dp
)
