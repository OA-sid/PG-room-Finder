package com.example.renthostelfinder.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.renthostelfinder.ui.theme.datas.HouseData
import com.example.renthostelfinder.ui.theme.viewModels.HomePageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    homePageViewModel: HomePageViewModel = viewModel(),
    onProfileClick: () -> Unit,
    onPostAdClick: () -> Unit
) {
    val houses by homePageViewModel.houses.collectAsState()
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home Page") },
                navigationIcon = {
                    IconButton(onClick = { menuExpanded = !menuExpanded }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Profile") },
                            onClick = {
                                menuExpanded = false
                                onProfileClick()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Post Ad") },
                            onClick = {
                                menuExpanded = false
                                onPostAdClick()
                            }
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(contentPadding = paddingValues) {
                items(houses) { house ->
                    HouseItem(house)
                }
            }
        }
    )
}

@Composable
fun HouseItem(house: HouseData) {
    Column(modifier = Modifier.padding(8.dp)) {
        if (house.imageUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(house.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        Text(text = house.title, style = MaterialTheme.typography.titleLarge)
        Text(text = house.description, style = MaterialTheme.typography.bodyLarge)
        Text(text = house.price, style = MaterialTheme.typography.titleMedium)
        Text(text = house.location, style = MaterialTheme.typography.bodyMedium)
    }
}
