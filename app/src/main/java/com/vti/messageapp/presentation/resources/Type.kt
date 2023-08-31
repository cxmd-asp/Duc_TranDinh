package com.vti.messageapp.presentation.resources

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.vti.messageapp.R

@OptIn(ExperimentalTextApi::class)
val MainFont = FontFamily(
    Font(R.font.roboto, weight = FontWeight.Normal, style = FontStyle.Normal)
)

val TextColorDefault = Color(0xFF222222)

@Immutable
data class AppTypography(
    val mainTextBold: TextStyle,
    val button: TextStyle,
    val title: TextStyle,
    val mainText: TextStyle,
    val miniText: TextStyle,
    val price: TextStyle
)

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        mainTextBold = TextStyle.Default,
        button = TextStyle.Default,
        title = TextStyle.Default,
        mainText = TextStyle.Default,
        miniText = TextStyle.Default,
        price = TextStyle.Default
    )
}

val Typography = AppTypography(
    mainTextBold = TextStyle(
        fontFamily = MainFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 18.sp,
        color = TextColorDefault
    ),
    button = TextStyle(
        fontFamily = MainFont,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        color = TextColorDefault
    ),
    title = TextStyle(
        fontFamily = MainFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 21.sp,
        color = TextColorDefault
    ),
    mainText = TextStyle(
        fontFamily = MainFont,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        color = TextColorDefault
    ),
    miniText = TextStyle(
        fontFamily = MainFont,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        color = TextColorDefault
    ),
    price = TextStyle(
        fontFamily = MainFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 21.sp,
        color = TextColorDefault
    )
)
