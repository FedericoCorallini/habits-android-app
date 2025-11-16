package com.fcorallini.home_presentation.detail

import java.time.DayOfWeek
import java.time.LocalTime

interface DetailEvent {
    data class ReminderChange(val time : LocalTime) : DetailEvent
    data class FrequencyChange(val dayOfWeek: DayOfWeek) : DetailEvent
    data class NameChange(val name : String) : DetailEvent
    data object HabitSave : DetailEvent
}