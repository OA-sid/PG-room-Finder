// EditAdScreen.kt
package com.example.renthostelfinder.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.renthostelfinder.ui.theme.viewModels.EditAdViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAdScreen(adId: String, navController: NavController, editAdViewModel: EditAdViewModel = viewModel()) {
    val ad by editAdViewModel.ad.collectAsState()

    LaunchedEffect(adId) {
        editAdViewModel.getAd(adId)
    }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    var submissionSuccess by remember { mutableStateOf<Boolean?>(null) }
    var submissionError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(ad) {
        ad?.let {
            title = it.title
            description = it.description
            price = it.price
            location = it.location
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Edit Ad", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            if (ad != null) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (isSubmitting) {
                    CircularProgressIndicator()
                } else {
                    Button(onClick = {
                        isSubmitting = true
                        submissionSuccess = null
                        submissionError = null
                        val updatedAd = ad!!.copy(
                            title = title,
                            description = description,
                            price = price,
                            location = location
                        )
                        editAdViewModel.updateHouse(updatedAd,
                            onSuccess = {
                                isSubmitting = false
                                submissionSuccess = true
                                navController.popBackStack()
                            },
                            onFailure = { exception ->
                                isSubmitting = false
                                submissionError = exception.message
                            }
                        )
                    }) {
                        Text("Update", fontSize = 20.sp)
                    }
                }

                submissionSuccess?.let {
                    if (it) {
                        Text("Update Successful", color = MaterialTheme.colorScheme.primary)
                    }
                }

                submissionError?.let {
                    Text("Update Failed: $it", color = MaterialTheme.colorScheme.error)
                }
            } else {
                Text("Loading ad details...", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
