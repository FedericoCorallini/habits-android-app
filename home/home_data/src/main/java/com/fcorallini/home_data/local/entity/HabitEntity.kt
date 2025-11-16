package com.fcorallini.home_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HabitEntity(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val name : String,
    val frequency : List<Int>,
    val reminder : Long,
    val completedDates : List<Long>,
    val startDate : Long
)
