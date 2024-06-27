package com.example.renthostelfinder.ui.theme.Components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DividerComponent(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(3.dp), thickness = 2.dp, color = Color.LightGray)
        Text(text = "or", fontSize = 18.sp, fontWeight = FontWeight.Normal)
        Divider(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(3.dp), thickness = 2.dp, color = Color.LightGray)
    }
}


@Composable
fun AlreadyAccountClickableText(onTextSelected: (String) -> Unit){
    val initialText = "Already have an account ? "
    val loginText = "Login"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Color.Black)){
            pushStringAnnotation(tag = loginText , annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(modifier = Modifier
        .fillMaxWidth()
        .heightIn(20.dp)
        ,style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),text = annotatedString, onClick = {
                offset -> annotatedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also { span ->
                Log.d("Login text is clicked", "{$span}")

                if (span.item == loginText){
                    onTextSelected(span.item)
                }
            }
        })
}