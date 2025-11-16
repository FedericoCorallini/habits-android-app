package com.fcorallini.home_presentation.home

import com.fcorallini.home_domain.model.Habit
import java.time.ZonedDateTime

interface HomeEvent {
    data class ChangeDate(val date : ZonedDateTime) : HomeEvent
    data class CompleteHabit(val habit: Habit) : HomeEvent
}