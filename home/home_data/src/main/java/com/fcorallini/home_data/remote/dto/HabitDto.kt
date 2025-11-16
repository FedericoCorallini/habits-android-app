package com.fcorallini.home_data.remote.dto

data class HabitDto(
    val name : String,
    val frequency : List<Int>,
    val reminder : Long,
    val completedDates : List<Long>?,
    val startDate : Long
)
