package com.fcorallini.authentication_presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.fcorallini.authentication_presentation.R
import com.fcorallini.authentication_presentation.login.components.LoginForm
import com.fcorallini.core_presentation.HabitsTitle

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLogIn : () -> Unit,
    onSignUp : () -> Unit
) {
    LaunchedEffect(viewModel.state.isLoggedIn) {
        if(viewModel.state.isLoggedIn){
            onLogIn()
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Login",
            contentScale = ContentScale.Crop,
            modifier = Modifier.aspectRatio(0.65f).graphicsLayer {
                scaleX = 1f
                scaleY = 1f
            }
        )
        Spacer(
            modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Transparent,
                        MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background

                    )
                )
            )
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier)
            Spacer(modifier = Modifier)
            HabitsTitle(title = "Welcome To\nMonumental Habits")
            LoginForm(viewModel.state, viewModel::onEvent, onSignUp)
        }
    }
}