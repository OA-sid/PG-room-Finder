// HomeApps.kt
package com.example.renthostelfinder.ui.theme.router

import ProfileScreen
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.renthostelfinder.ui.theme.screens.*

@Composable
fun HomeApps() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Crossfade(targetState = AppRouter.currentScreen, label = "") { currentState ->
            when (currentState.value) {
                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }
                is Screen.SignInScreen -> {
                    SignInScreen()
                }
                is Screen.HomeScreen -> {
                    HomePage(
                        onProfileClick = { AppRouter.navigateTo(Screen.ProfileScreen) },
                        onPostAdClick = { AppRouter.navigateTo(Screen.PostScreen) }
                    )
                }
                is Screen.PostScreen -> {
                    PostAdPage()
                }
                is Screen.DetailScreen -> {
                    DetailPage()
                }
                is Screen.ProfileScreen -> {
                    ProfileScreen(
                        onLogout = { AppRouter.navigateTo(Screen.SignInScreen) },
                        onUpdateProfile = { AppRouter.navigateTo(Screen.ProfileUpdateScreen) }
                    )
                }
                is Screen.ProfileUpdateScreen -> {
                    ProfileUpdateScreen()
                }
                is Screen.MyAdScreen -> {
                    MyAdsScreen(
                        onEditAdClick = { adId -> AppRouter.navigateTo(Screen.EditAdScreen(adId)) }
                    )
                }
                is Screen.EditAdScreen -> {
                    val adId = (currentState.value as Screen.EditAdScreen).adId
                    Screen.EditAdScreen(adId = adId)
                }
            }
        }
    }
}
