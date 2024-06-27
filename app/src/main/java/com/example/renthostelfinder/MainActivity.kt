package com.example.renthostelfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.renthostelfinder.ui.theme.RentHostelFinderTheme
import com.example.renthostelfinder.ui.theme.router.HomeApps


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RentHostelFinderTheme {
                HomeApps()
                }
            }
        }
    }

