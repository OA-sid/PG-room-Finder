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
import com.example.renthostelfinder.ui.theme.viewModels.ProfileUpdateViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileUpdateScreen(profileUpdateViewModel: ProfileUpdateViewModel = viewModel()) {
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
                "Profile update failed: ${it.exceptionOrNull()?.message}"
            }
            snackbarHostState.showSnackbar(message)
            if (it.isSuccess) {
                AppRouter.navigateTo(Screen.ProfileScreen)
            }
        }
    }

    fun updateUserProfile() {
        val user = hashMapOf(
            "email" to email,
            "phoneNumber" to phoneNumber,
            "studentID" to studentID,
            "gender" to gender
        )

        // First, update Firestore
        FirebaseFirestore.getInstance().collection("users")
            .document(currentUser!!.uid)
            .set(user)
            .addOnSuccessListener {
                // If Firestore update is successful, proceed to update Firebase Auth profile
                val profileUpdates = userProfileChangeRequest {
                    // Set the email here if you want to update it
                    // Other profile attributes can be updated similarly
                }

                currentUser.updateEmail(email).addOnCompleteListener { emailUpdateTask ->
                    if (emailUpdateTask.isSuccessful) {
                        currentUser.updateProfile(profileUpdates)
                            .addOnCompleteListener { profileUpdateTask ->
                                if (profileUpdateTask.isSuccessful) {
                                    updateState = Result.success(true)
                                } else {
                                    updateState = Result.failure(profileUpdateTask.exception ?: Exception("Profile update failed"))
                                }
                            }
                    } else {
                        updateState = Result.failure(emailUpdateTask.exception ?: Exception("Email update failed"))
                    }
                }
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            TextField(
                value = studentID,
                onValueChange = { studentID = it },
                label = { Text("Student ID") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = gender == "male", onClick = { gender = "male" })
                Text("Male", color = Color.White, modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(selected = gender == "female", onClick = { gender = "female" })
                Text("Female", color = Color.White, modifier = Modifier.padding(start = 8.dp))
            }

            Button(
                onClick = { updateUserProfile() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
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
