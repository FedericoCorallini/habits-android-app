package com.fcorallini.home_domain.alarm

import com.fcorallini.home_domain.model.Habit

interface AlarmHandler {
    fun setRecurringAlarm(habit : Habit)
    fun cancel(habit: Habit)
}