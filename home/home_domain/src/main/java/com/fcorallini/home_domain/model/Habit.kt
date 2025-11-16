package com.fcorallini.home_domain.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

data class Habit(
    val id : String,
    val name : String,
    val frequency : List<DayOfWeek>,
    val reminder : LocalTime,
    val completedDates : List<LocalDate>,
    val startDate : ZonedDateTime
)
