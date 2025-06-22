package com.fcorallini.habits.authentication.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.fcorallini.habits.R
import com.fcorallini.habits.authentication.presentation.signup.components.SignupForm

@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    onSingIn : () -> Unit,
    onLogIn : () -> Unit
) {
    val state = viewModel.state
    LaunchedEffect(state.isSignedIn) {
        if(state.isSignedIn) {
            onSingIn()
        }
    }

    LaunchedEffect(state.logIn) {
        if(state.logIn) {
            onLogIn()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = painterResource(id = R.drawable.create),
            contentDescription = null,
            modifier = Modifier.aspectRatio(2f),
            contentScale = ContentScale.FillHeight
        )
        SignupForm(viewModel.state, viewModel::onEvent, modifier = Modifier.fillMaxWidth())
    }

    if(viewModel.state.isLoading){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }
}