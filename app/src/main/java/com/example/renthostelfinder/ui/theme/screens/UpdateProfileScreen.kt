package com.example.renthostelfinder.ui.theme.screens

/*

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
import androidx.navigation.NavHostController
import com.example.renthostelfinder.ui.theme.backButtonHandler.SystemBackButtonHandler
import com.example.renthostelfinder.ui.theme.router.AppRouter
import com.example.renthostelfinder.ui.theme.router.Screen
import com.example.renthostelfinder.ui.theme.viewModels.ProfileUpdateViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileUpdateScreen(navController: NavHostController, profileUpdateViewModel: ProfileUpdateViewModel = viewModel()) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var phoneNumber by remember { mutableStateOf("") }
    var studentID by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("male") }
    var updateState by remember { mutableStateOf<Result<Boolean>?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(updateState) {
        updateState?.let {
            val message = if (it.isSuccess) {
                "Profile updated successfully!"
            } else {
                "Profile update failed."
            }
            snackbarHostState.showSnackbar(message)
        }
    }

    fun updateUserProfile() {
        val user = hashMapOf(
            "email" to email,
            "phoneNumber" to phoneNumber,
            "studentID" to studentID,
            "gender" to gender
        )
        FirebaseFirestore.getInstance().collection("users")
            .document(currentUser!!.uid)
            .set(user)
            .addOnSuccessListener {
                updateState = Result.success(true)
            }
            .addOnFailureListener {
                updateState = Result.failure(it)
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
                text = "Update Profile", color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center,
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
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

            Button(
                onClick = { updateUserProfile() },
            ) {
                Text("Update Profile", fontSize = 25.sp)
            }
            SnackbarHost(hostState = snackbarHostState)
        }
        SystemBackButtonHandler {
            AppRouter.navigateTo(Screen.SignInScreen)
        }
    }
}
*/
