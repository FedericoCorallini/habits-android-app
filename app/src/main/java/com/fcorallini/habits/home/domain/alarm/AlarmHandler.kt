package com.fcorallini.habits.home.domain.alarm

import com.fcorallini.habits.home.domain.model.Habit

interface AlarmHandler {
    fun setRecurringAlarm(habit : Habit)
    fun cancel(habit: Habit)
}