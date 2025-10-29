package com.fcorallini.habits.home.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fcorallini.habits.core.presentation.HabitTextField
import com.fcorallini.habits.home.presentation.detail.components.DetailFrequency
import com.fcorallini.habits.home.presentation.detail.components.DetailReminder
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel : DetailViewModel = hiltViewModel(),
    onBack : () -> Unit,
    onSave : () -> Unit,
    habitId : String?
) {
    val state = viewModel.state
    val clock = rememberSheetState()
    ClockDialog(
        state = clock,
        selection = ClockSelection.HoursMinutes {
            hours, minutes ->
            viewModel.onEvent(DetailEvent.ReminderChange(LocalTime.of(hours, minutes)))
        },
        config = ClockConfig(
            defaultTime = state.reminder,
            is24HourFormat = true
        )
    )

    LaunchedEffect(state.isSaved) {
        if(state.isSaved) {
            onSave()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "New Habit") },
                navigationIcon = {
                    IconButton(
                        onClick = { onBack() }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(DetailEvent.HabitSave) },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Create habit",
                    tint = MaterialTheme.colorScheme.tertiary)
            }
        }
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HabitTextField(
                value = state.habitName,
                onValueChange = { viewModel.onEvent(DetailEvent.NameChange(it)) },
                placeholder = "New Habit",
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White,
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Done
                ), keyboardActions = KeyboardActions(
                    onAny = { viewModel.onEvent(DetailEvent.HabitSave) }
                ),
                contentDescription = "Enter habit name"
            )
            DetailFrequency(
                selectedDays = state.frequency,
                onFrequencyChange = { viewModel.onEvent(DetailEvent.FrequencyChange(it)) }
            )
            DetailReminder(
                reminder = state.reminder,
                onTimeClick = { clock.show() }
            )
        }

    }
}