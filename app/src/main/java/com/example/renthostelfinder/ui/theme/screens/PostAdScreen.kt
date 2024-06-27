// PostAdScreen.kt
package com.example.renthostelfinder.ui.theme.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.renthostelfinder.ui.theme.router.AppRouter
import com.example.renthostelfinder.ui.theme.router.Screen
import com.example.renthostelfinder.ui.theme.viewModels.PostAdViewModel

@Composable
fun PostAdPage(postAdViewModel: PostAdViewModel = viewModel()) {
    val house by postAdViewModel.house.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val painter = rememberAsyncImagePainter(imageUri)
    var isSubmitting by remember { mutableStateOf(false) }
    var submissionSuccess by remember { mutableStateOf<Boolean?>(null) }
    var submissionError by remember { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.Blue,
                    Color.Red,
                    Color.Green
                )
            )
        )
        .padding(20.dp),
        contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Post Your Ad",
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = house.title,
                onValueChange = { postAdViewModel.updateHouse(house.copy(title = it)) },
                label = { Text("Title") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = house.description,
                onValueChange = { postAdViewModel.updateHouse(house.copy(description = it)) },
                label = { Text("Description") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = house.price,
                onValueChange = { postAdViewModel.updateHouse(house.copy(price = it)) },
                label = { Text("Price") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = house.location,
                onValueChange = { postAdViewModel.updateHouse(house.copy(location = it)) },
                label = { Text("Location") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { imagePickerLauncher.launch("image/*") }, Modifier.align(alignment = Alignment.Start)) {
                Text("Select Image")
            }
            Spacer(modifier = Modifier.height(8.dp))

            imageUri?.let {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isSubmitting) {
                CircularProgressIndicator()
            } else {
                Button(onClick = {
                    isSubmitting = true
                    submissionSuccess = null
                    submissionError = null
                    postAdViewModel.saveHouseToFirebase(
                        imageUri,
                        onSuccess = {
                            isSubmitting = false
                            submissionSuccess = true
                            AppRouter.navigateTo(Screen.MyAdScreen)  // Navigate to MyAdsScreen after successful submission
                        },
                        onFailure = { exception ->
                            isSubmitting = false
                            submissionError = exception.message
                        }
                    )
                }) {
                    Text("Submit", fontSize = 20.sp)
                }
            }

            submissionSuccess?.let {
                if (it) {
                    Text("Submission Successful", color = MaterialTheme.colorScheme.primary)
                }
            }

            submissionError?.let {
                Text("Submission Failed: $it", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
