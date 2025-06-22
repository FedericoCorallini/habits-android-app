package com.fcorallini.habits.home.presentation.home

import com.fcorallini.habits.home.domain.model.Habit
import java.time.ZonedDateTime

interface HomeEvent {
    data class ChangeDate(val date : ZonedDateTime) : HomeEvent
    data class CompleteHabit(val habit: Habit) : HomeEvent
}