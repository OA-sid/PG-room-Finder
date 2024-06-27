package com.example.renthostelfinder.ui.theme.Components

import android.util.Log
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.example.renthostelfinder.ui.theme.router.AppRouter
import com.example.renthostelfinder.ui.theme.router.Screen
import com.example.renthostelfinder.ui.theme.screens.SignUpScreen

@Composable
fun NoAccountText(){
    val initialText = "Don't have an account?"
    val registerText = "Register"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Color.Blue)){
            pushStringAnnotation(tag = registerText, annotation = registerText)
            append(registerText)
        }
    }

    ClickableText(style = TextStyle(
        textAlign = TextAlign.Center
    ),text = annotatedString, onClick = {

            offset -> annotatedString.getStringAnnotations(offset,offset)

        .firstOrNull()?.also { span ->
            Log.d("Register","{$span}")

        }
        AppRouter.navigateTo(Screen.SignUpScreen)
    })
}