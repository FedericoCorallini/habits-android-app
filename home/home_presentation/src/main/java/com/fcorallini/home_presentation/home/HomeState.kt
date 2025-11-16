package com.fcorallini.home_presentation.home

import com.fcorallini.home_domain.model.Habit

import java.time.ZonedDateTime

data class HomeState(
    val currentDate : ZonedDateTime = ZonedDateTime.now(),
    val selectedDate : ZonedDateTime = ZonedDateTime.now(),
    val habits : List<Habit> = emptyList()
)
