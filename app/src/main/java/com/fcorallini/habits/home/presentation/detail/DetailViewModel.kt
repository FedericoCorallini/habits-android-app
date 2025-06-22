package com.fcorallini.habits.home.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fcorallini.habits.home.domain.detail.usecases.GetHabitByIdUseCase
import com.fcorallini.habits.home.domain.detail.usecases.InsertHabitUseCase
import com.fcorallini.habits.home.domain.model.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val insertHabitUseCase: InsertHabitUseCase,
    private val getHabitByIdUseCase: GetHabitByIdUseCase
) : ViewModel() {

    var state by mutableStateOf(DetailState())
        private set

    private val habitId: String? = savedStateHandle["habitId"]

    init {
        if(habitId != null){
            viewModelScope.launch {
                val habit = getHabitByIdUseCase(habitId)
                state = state.copy(
                    id = habit.id,
                    habitName = habit.name,
                    frequency = habit.frequency,
                    reminder = habit.reminder,
                    completedDates = habit.completedDates,
                    startDate = habit.startDate
                )
            }
        }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.ReminderChange -> {
                state = state.copy(reminder = event.time)
            }
            is DetailEvent.NameChange -> {
                state = state.copy(habitName = event.name)
            }
            is DetailEvent.FrequencyChange -> {
                val frequency = if (state.frequency.contains(event.dayOfWeek)) {
                    state.frequency - event.dayOfWeek
                }
                else {
                    state.frequency + event.dayOfWeek
                }
                state = state.copy(frequency = frequency)
            }
            DetailEvent.HabitSave -> {
                val habit = Habit(
                    id = habitId ?: UUID.randomUUID().toString(),
                    name = state.habitName,
                    frequency = state.frequency,
                    reminder = state.reminder,
                    completedDates = state.completedDates,
                    startDate = state.startDate
                )
                viewModelScope.launch {
                    insertHabitUseCase(habit)
                }
                state = state.copy(isSaved = true)
            }

        }
    }
}