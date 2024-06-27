package com.example.renthostelfinder.ui.theme.router

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


sealed class Screen {
    data object SignUpScreen: Screen()
    data object SignInScreen: Screen()
    data object PostScreen: Screen()
    data object DetailScreen: Screen()
    data object HomeScreen: Screen()
    data object ProfileScreen: Screen()
    data object ProfileUpdateScreen: Screen()
    data object MyAdScreen: Screen()
    data class EditAdScreen(val adId: String) : Screen()


}

object AppRouter{
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.SignUpScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
}