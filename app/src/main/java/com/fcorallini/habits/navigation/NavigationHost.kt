package com.fcorallini.habits.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.fcorallini.habits.authentication.presentation.login.LoginScreen
import com.fcorallini.habits.authentication.presentation.signup.SignupScreen
import com.fcorallini.habits.home.presentation.detail.DetailScreen
import com.fcorallini.habits.home.presentation.home.HomeScreen
import com.fcorallini.habits.onboarding.presentation.OnboardingScreen
import com.fcorallini.habits.settings.presentation.SettingsScreen

@Composable
fun NavigationHost(startDestination : Routes, logout : () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination){
        composable<Routes.Login> {
            LoginScreen(
                onLogIn =  {
                    navController.popBackStack()
                    navController.navigate(Routes.Home)
                },
                onSignUp = {
                    navController.navigate(Routes.SignUp)
                }
            )
        }
        composable<Routes.Onboarding> {
            OnboardingScreen {
                navController.popBackStack()
                navController.navigate(Routes.Login)
            }
        }
        composable<Routes.Home> {
            HomeScreen(
                onSettings = {
                    navController.navigate(Routes.Settings)
                },
                onNewHabit = {
                    navController.navigate(Routes.Detail(null))
                },
                onEditHabit = { habit ->
                    navController.navigate(Routes.Detail(habit.id))
                }
            )
        }
        composable<Routes.Detail> {
            val detailRoute : Routes.Detail = it.toRoute()
            DetailScreen(
                onBack = {
                    navController.popBackStack()
                },
                onSave = {
                    navController.popBackStack()
                },
                habitId = detailRoute.habitId
            )
        }
        composable<Routes.SignUp> {
            SignupScreen(
                onLogIn = {
                    navController.popBackStack()
                },
                onSingIn = {
                    navController.popBackStack(Routes.Login, inclusive = true)
                    navController.navigate(Routes.Home)
                }
            )
        }
        composable<Routes.Settings> {
            SettingsScreen(
                onBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    logout()
                    navController.popBackStack(0, inclusive = true)
                    navController.navigate(Routes.Login)
                }
            )
        }
    }
}