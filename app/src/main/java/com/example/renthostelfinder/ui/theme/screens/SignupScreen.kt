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
import com.example.renthostelfinder.ui.theme.backButtonHandler.SystemBackButtonHandler
import com.example.renthostelfinder.ui.theme.router.AppRouter
import com.example.renthostelfinder.ui.theme.router.Screen
import com.example.renthostelfinder.ui.theme.viewModels.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern

@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var studentID by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("male") }
    var userType by remember { mutableStateOf("borrower") }
    var signUpState by remember { mutableStateOf<Result<Boolean>?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    var emailError by remember { mutableStateOf(false) }

    LaunchedEffect(signUpState) {
        signUpState?.let {
            val message = if (it.isSuccess) {
                "Sign up successful as $userType! Please login."
            } else {
                "Sign up failed. User type: $userType"
            }
            snackbarHostState.showSnackbar(message)
            AppRouter.navigateTo(Screen.SignInScreen)
        }
    }

    fun signUpUser() {
        if (email.isNotBlank() && password.isNotBlank() && !emailError) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = hashMapOf(
                            "email" to email,
                            "phoneNumber" to phoneNumber,
                            "studentID" to studentID,
                            "gender" to gender,
                            "userType" to userType
                        )
                        FirebaseFirestore.getInstance().collection("users")
                            .document(FirebaseAuth.getInstance().currentUser!!.uid)
                            .set(user)
                            .addOnSuccessListener {
                                signUpState = Result.success(true)
                            }
                            .addOnFailureListener {
                                signUpState = Result.failure(it)
                            }
                    } else {
                        signUpState = Result.failure(task.exception!!)
                    }
                }
        }
    }

    Box(
        modifier = Modifier
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
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "SignUp", color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center,
            )

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !Pattern.compile(
                        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                    ).matcher(email).matches()
                },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailError
            )
            if (emailError) {
                Text(
                    text = "Invalid email address",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            TextField(
                value = studentID,
                onValueChange = { studentID = it },
                label = { Text("Student ID") },
            )

            Row {
                RadioButton(selected = gender == "male", onClick = { gender = "male" })
                Text("Male", color = Color.White)
                RadioButton(selected = gender == "female", onClick = { gender = "female" })
                Text("Female", color = Color.White)
            }

            Row {
                RadioButton(selected = userType == "borrower", onClick = { userType = "borrower" })
                Text("Borrower", color = Color.White)
                RadioButton(selected = userType == "owner", onClick = { userType = "owner" })
                Text("Owner", color = Color.White)
            }

            Button(
                onClick = { signUpUser() },
                enabled = !emailError && email.isNotBlank() && password.isNotBlank()
            ) {
                Text("Sign Up", fontSize = 25.sp)
            }

            SnackbarHost(hostState = snackbarHostState)
        }
        SystemBackButtonHandler {
            AppRouter.navigateTo(Screen.SignInScreen)
        }
    }
}
