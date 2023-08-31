package com.vti.messageapp.presentation.view.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vti.messageapp.R
import com.vti.messageapp.presentation.resources.AppTheme

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onTextChange: (String) -> Unit,
    placeHolder: String = "",
    maxLength: Int? = null,
    singleLine: Boolean = true,
    isPasswordInput: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = FocusRequester(),
    trailing: (@Composable RowScope.() -> Unit)? = null,
    placeholderStyle: TextStyle? = null,
    hasBorder: Boolean = true,
) {
    var isShowPassWord by remember { mutableStateOf(false) }

    val icon =
        if (isShowPassWord) painterResource(id = R.drawable.ic_launcher_background) else painterResource(id = R.drawable.ic_launcher_foreground)
    val iconColor =
        if (isShowPassWord) AppTheme.colors.mainText else AppTheme.colors.placeholderText

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value,
            onValueChange = {
                if (maxLength == null) {
                    onTextChange.invoke(it)
                } else {
                    if (it.length <= maxLength) {
                        onTextChange.invoke(it)
                    }
                }
            },
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (isPasswordInput) {
                if (isShowPassWord) VisualTransformation.None else PasswordVisualTransformation('*')
            } else {
                VisualTransformation.None
            },
            textStyle = AppTheme.typography.mainText.copy(color = AppTheme.colors.mainText),
            modifier = Modifier.focusRequester(focusRequester)
        ) { innerTextField ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (hasBorder) Modifier
                            .border(
                                0.5.dp,
                                color = AppTheme.colors.borderStroke,
                                shape = RoundedCornerShape(AppTheme.shapes.wide)
                            ) else Modifier
                    ),
                color = AppTheme.colors.subBackground,
                shape = RoundedCornerShape(AppTheme.shapes.wide)
            ) {
                Box(
                    modifier = Modifier.padding(
                        start = 24.dp,
                        top = 13.dp,
                        bottom = 13.dp,
                        end = if (isPasswordInput || trailing != null) 12.dp else 24.dp
                    )
                ) {
                    Row {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    placeHolder,
                                    style = placeholderStyle ?: AppTheme.typography.mainText,
                                    color = AppTheme.colors.placeholderText
                                )
                            }
                            innerTextField()
                        }
                        if (trailing != null) {
                            trailing()
                        } else if (isPasswordInput && value.isNotEmpty()) {
                            IconButton(
                                onClick = { isShowPassWord = !isShowPassWord },
                                modifier = Modifier.wrapContentSize()
                            ) {
                                Icon(icon, null, tint = iconColor)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Preview(name = "AppTextField")
@Composable
private fun PreviewAppTextField() {
    AppTextField(
        modifier = Modifier
            .height(44.dp)
            .fillMaxWidth(),
        value = "",
        onTextChange = {},
        placeHolder = "従業員ID"
    )
}
