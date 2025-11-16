package com.fcorallini.authentication_presentation.signup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.fcorallini.authentication_presentation.signup.SignupEvent
import com.fcorallini.authentication_presentation.signup.SignupState
import com.fcorallini.core_presentation.HabitPasswordTextField
import com.fcorallini.core_presentation.HabitTextField
import com.fcorallini.core_presentation.HabitsTitle

@Composable
fun SignupForm(
    state : SignupState,
    onEvent : (SignupEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Column (modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        HabitsTitle("Create your account")
        Spacer(modifier = Modifier.height(32.dp))
        HabitTextField(
            value = state.email,
            onValueChange = { onEvent(SignupEvent.EmailChanged(it)) },
            placeholder = "Enter email",
            contentDescription = "Enter email",
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            leadingIcon = Icons.Outlined.Email,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .padding(horizontal = 20.dp),
            keyboardActions = KeyboardActions(
                onAny = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            ),
            errorMessage = state.emailError,
            isEnabled = !state.isLoading,
            backgroundColor = Color.White
        )
        HabitPasswordTextField(
            value = state.password,
            onValueChange = { onEvent(SignupEvent.PasswordChanged(it)) },
            placeholder = "Enter password",
            contentDescription = "Enter password",
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .padding(horizontal = 20.dp),
            keyboardActions = KeyboardActions(
                onAny = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            ),
            errorMessage = state.passwordError,
            isEnabled = !state.isLoading,
            backgroundColor = Color.White
        )
        Spacer(Modifier.height(32.dp))
        com.fcorallini.core_presentation.HabitsButton(
            text = "Create an Account",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            isEnabled = !state.isLoading
        ) {
            onEvent(SignupEvent.SignUp)
        }
        TextButton(
            onClick = { onEvent(SignupEvent.LogIn) }
        ) {
            Text(
                text = "Already have an account?",
                color = MaterialTheme.colorScheme.tertiary,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}