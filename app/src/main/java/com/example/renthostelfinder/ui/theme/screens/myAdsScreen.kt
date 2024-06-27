// MyAdsScreen.kt
package com.example.renthostelfinder.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.renthostelfinder.ui.theme.datas.HouseData
import com.example.renthostelfinder.ui.theme.viewModels.MyAdsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAdsScreen(onEditAdClick: (String) -> Unit, myAdsViewModel: MyAdsViewModel = viewModel()) {
    val ads by myAdsViewModel.ads.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Ads") })
        },
        content = { paddingValues ->
            if (ads.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("0 ads posted yet", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(contentPadding = paddingValues) {
                    items(ads) { ad ->
                        AdItem(ad, onEditAdClick, myAdsViewModel)
                    }
                }
            }
        }
    )
}

@Composable
fun AdItem(ad: HouseData, onEditAdClick: (String) -> Unit, myAdsViewModel: MyAdsViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Ad") },
            text = { Text("Are you sure you want to delete this ad?") },
            confirmButton = {
                TextButton(onClick = {
                    myAdsViewModel.deleteAd(ad.id, onSuccess = {
                        showDialog = false
                    }, onFailure = { exception ->
                        // Handle error
                    })
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(modifier = Modifier.padding(8.dp)) {
        if (ad.imageUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(ad.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        Text(text = ad.title, style = MaterialTheme.typography.titleLarge)
        Text(text = ad.description, style = MaterialTheme.typography.bodyLarge)
        Text(text = ad.price, style = MaterialTheme.typography.titleMedium)
        Text(text = ad.location, style = MaterialTheme.typography.bodyMedium)

        Row {
            TextButton(onClick = {
                onEditAdClick(ad.id)
            }) {
                Text("Edit", color = MaterialTheme.colorScheme.primary)
            }
            TextButton(onClick = {
                showDialog = true
            }) {
                Text("Delete", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
