package com.example.renthostelfinder.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.renthostelfinder.ui.theme.backButtonHandler.SystemBackButtonHandler
import com.example.renthostelfinder.ui.theme.router.AppRouter
import com.example.renthostelfinder.ui.theme.router.Screen
import com.example.renthostelfinder.ui.theme.viewModels.DetailPageViewModel

@Composable
fun DetailPage(detailPageViewModel: DetailPageViewModel = viewModel()) {
    val house by detailPageViewModel.house.collectAsState()
    house?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            if (it.imageUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(it.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            Text(text = it.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.description, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.price, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.location, style = MaterialTheme.typography.bodyMedium)
            SystemBackButtonHandler {
                AppRouter.navigateTo(Screen.HomeScreen)
            }
        }
    }
}