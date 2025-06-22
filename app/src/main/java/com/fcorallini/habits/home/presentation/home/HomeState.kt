package com.fcorallini.habits.home.presentation.home

import com.fcorallini.habits.home.domain.model.Habit
import java.time.LocalTime
import java.time.ZonedDateTime

data class HomeState(
    val currentDate : ZonedDateTime = ZonedDateTime.now(),
    val selectedDate : ZonedDateTime = ZonedDateTime.now(),
    val habits : List<Habit> = emptyList()
)
