package com.vti.messageapp.presentation.resources

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val primaryColor: Color,
    val background: Color,
    val primary: Color,
    val borderStroke: Color,
    val secondaryColor: Color,
    val subBackground: Color,
    val textButtonColor: Color,
    val subText: Color,
    val mainText: Color,
    val oppositeTextColor: Color,
    val placeholderText: Color,
    val backgroundImage: Color,
    val label: Color,
    val secondaryButton: List<Color>,
    val primaryButton: List<Color>,
    val checkInByQrButton: List<Color>,
    val checkInLocationButton: List<Color>,
    val scrimColor: Color,
    val radioButtonColors: Color
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        primaryColor = Color.Unspecified,
        background = Color.Unspecified,
        primary = Color.Unspecified,
        borderStroke = Color.Unspecified,
        secondaryColor = Color.Unspecified,
        subBackground = Color.Unspecified,
        textButtonColor = Color.Unspecified,
        subText = Color.Unspecified,
        mainText = Color.Unspecified,
        oppositeTextColor = Color.Unspecified,
        placeholderText = Color.Unspecified,
        backgroundImage = Color.Unspecified,
        label = Color.Unspecified,
        secondaryButton = emptyList(),
        primaryButton = emptyList(),
        checkInByQrButton = emptyList(),
        checkInLocationButton = emptyList(), scrimColor = Color.Unspecified,
        radioButtonColors = Color.Unspecified
    )
}

val Colors = AppColors(
    primaryColor = Color(0xFF162D7A),
    background = Color(0xFFF8F8F9),
    primary = Color(0xFFE8380D),
    borderStroke = Color(0xFFDCDCDC),
    secondaryColor = Color(0xFF0085FF),
    subBackground = Color(0xFFFFFFFF),
    textButtonColor = Color(0xFFFFFFFF),
    subText = Color(0xFF666666),
    mainText = Color(0xFF222222),
    oppositeTextColor = Color(0xFFFFFFFF),
    placeholderText = Color(0xFF999999),
    backgroundImage = Color(0xFFE4E4E4),
    label = Color(0xFFFF6969),
    secondaryButton = listOf(Color(0xFF08D6BC), Color(0xFF00C2B8)),
    primaryButton = listOf(Color(0xFFFF9A15), Color(0xFFED6A24)),
    checkInByQrButton = listOf(Color(0xFFFF9F1D), Color(0xFFF76524)),
    checkInLocationButton = listOf(Color(0xFF0AD6BB), Color(0xFF00BFBC)),
    scrimColor = Color(0xFF313131),
    radioButtonColors = Color(0xFF08D6BC)

)
