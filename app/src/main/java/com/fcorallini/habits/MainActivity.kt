package com.fcorallini.habits

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.fcorallini.habits.navigation.NavigationHost
import com.fcorallini.habits.navigation.Routes
import com.fcorallini.habits.ui.theme.HabitsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitsTheme {
                NavigationHost(getStartDestination(), viewModel::logout)
            }
        }
    }

    private fun getStartDestination() : Routes {
        return when {
            viewModel.isLoggedIn -> Routes.Home
            viewModel.hasSeenOnboarding -> Routes.Login
            else -> Routes.Onboarding
        }
    }
}
