package com.fcorallini.home_presentation.home

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fcorallini.home_domain.model.Habit
import com.fcorallini.home_presentation.R
import com.fcorallini.home_presentation.home.HomeEvent.ChangeDate
import com.fcorallini.home_presentation.home.HomeEvent.CompleteHabit
import com.fcorallini.home_presentation.home.components.HomeAskPermission
import com.fcorallini.home_presentation.home.components.HomeDateSelector
import com.fcorallini.home_presentation.home.components.HomeHabit
import com.fcorallini.home_presentation.home.components.HomeQuote

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNewHabit : () -> Unit,
    onSettings : () -> Unit,
    onEditHabit : (Habit) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Home") },
                navigationIcon = {
                    IconButton(
                        onClick = { onSettings() }
                    ) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNewHabit() },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Create habit",
                    tint = MaterialTheme.colorScheme.tertiary)
            }
        }
    ) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            HomeAskPermission(Manifest.permission.POST_NOTIFICATIONS)
        }
        LazyColumn(modifier = Modifier
            .padding(it)
            .padding(start = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            item {
                HomeQuote(
                    quote = "We first make our habits and then our habits make us",
                    author = "Anonymous",
                    imageId = R.drawable.illustration,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).padding(end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Habits".uppercase(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    HomeDateSelector(
                        selectedDay = state.selectedDate,
                        mainDate = state.currentDate,
                        onDateClick = { date -> viewModel.onEvent(ChangeDate(date)) },
                        datesToShow = 4
                    )
                }
            }
            items(state.habits) { habit ->
                HomeHabit(
                    habit = habit,
                    selectedDate = state.selectedDate.toLocalDate(),
                    onCheckedChange = { viewModel.onEvent(CompleteHabit(habit))},
                    onHabitClick = { onEditHabit(habit) }
                )
            }
        }
    }
}