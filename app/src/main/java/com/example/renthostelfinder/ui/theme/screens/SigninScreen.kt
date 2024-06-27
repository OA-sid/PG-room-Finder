// SignInScreen.kt
package com.example.renthostelfinder.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.renthostelfinder.ui.theme.Components.NoAccountText
import com.example.renthostelfinder.ui.theme.backButtonHandler.SystemBackButtonHandler
import com.example.renthostelfinder.ui.theme.router.AppRouter
import com.example.renthostelfinder.ui.theme.router.Screen
import com.example.renthostelfinder.ui.theme.viewModels.SignInViewModel
import com.example.renthostelfinder.ui.theme.viewModels.SignUpViewModel

@Composable
fun SignInScreen(signInViewModel: SignInViewModel = viewModel(), signUpViewModel: SignUpViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var signInError by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.Red,
                    Color.Black,
                    Color.Green
                )
            )
        )
        .padding(20.dp),
        contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Text(
                text = "Login",
                color = Color.White,
                fontSize = 50.sp,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            if (signInError) {
                Text(text = "Sign-in failed. Please check your credentials.", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                signInViewModel.signIn(email, password, { userType ->
                    if (userType == "owner") {
                        AppRouter.navigateTo(Screen.MyAdScreen)
                    } else if (userType == "borrower") {
                        AppRouter.navigateTo(Screen.HomeScreen)
                    }
                }, {
                    signInError = true
                })
            }) {
                Text("Sign In", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            NoAccountText()

        }
        SystemBackButtonHandler {
            AppRouter.navigateTo(Screen.SignUpScreen)
        }
    }
    SnackbarHost(hostState = snackbarHostState)
}
