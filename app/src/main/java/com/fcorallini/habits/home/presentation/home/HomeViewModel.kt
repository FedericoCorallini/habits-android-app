package com.fcorallini.habits.home.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fcorallini.habits.home.domain.home.usecases.CompleteHabitUseCase
import com.fcorallini.habits.home.domain.home.usecases.GetHabitsForDateUseCase
import com.fcorallini.habits.home.domain.home.usecases.SyncHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHabitsForDateUseCase: GetHabitsForDateUseCase,
    private val completeHabitUseCase: CompleteHabitUseCase,
    private val syncHabitsUseCase : SyncHabitsUseCase,
) : ViewModel() {

    private var job : Job? = null
    var state by mutableStateOf(HomeState())

    init {
        getHabits()
        viewModelScope.launch {
            syncHabitsUseCase()
        }
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.ChangeDate -> {
                state = state.copy(
                    selectedDate = event.date
                )
                getHabits()
            }
            is HomeEvent.CompleteHabit -> {
                viewModelScope.launch {
                    completeHabitUseCase(event.habit, date = state.selectedDate)
                }
            }
        }
    }

    private fun getHabits() {
        job?.cancel()
        job = viewModelScope.launch {
            getHabitsForDateUseCase(state.selectedDate).collectLatest {
                state = state.copy(habits = it)
            }
        }
    }
}