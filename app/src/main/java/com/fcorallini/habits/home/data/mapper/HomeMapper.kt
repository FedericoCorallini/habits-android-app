package com.fcorallini.habits.home.data.mapper

import com.fcorallini.habits.home.data.extension.toStartOfDateTimestamp
import com.fcorallini.habits.home.data.extension.toTimeStamp
import com.fcorallini.habits.home.data.extension.toZoneDateTime
import com.fcorallini.habits.home.data.local.entity.HabitEntity
import com.fcorallini.habits.home.data.local.entity.HabitSyncEntity
import com.fcorallini.habits.home.data.remote.dto.HabitDto
import com.fcorallini.habits.home.data.remote.dto.HabitResponse
import com.fcorallini.habits.home.domain.model.Habit
import java.time.DayOfWeek

fun HabitEntity.toDomain() : Habit {
    return Habit(
        id = id,
        name = name,
        frequency = frequency.map { DayOfWeek.of(it) },
        completedDates = completedDates.map { it.toZoneDateTime().toLocalDate() },
        reminder = reminder.toZoneDateTime().toLocalTime(),
        startDate = startDate.toZoneDateTime()
    )
}

fun Habit.toEntity() : HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        frequency = frequency.map { it.value },
        completedDates = completedDates.map { it.toZoneDateTime().toTimeStamp() },
        reminder = reminder.toZoneDateTime().toTimeStamp(),
        startDate = startDate.toStartOfDateTimestamp()
    )
}

fun HabitResponse.toDomain() : List<Habit> {
    return this.entries.map {
        val id = it.key
        val dto = it.value
        Habit(
            id = id,
            name = dto.name,
            frequency = dto.frequency.map { DayOfWeek.of(it) },
            completedDates = dto.completedDates?.map { it.toZoneDateTime().toLocalDate() } ?: emptyList(),
            reminder = dto.reminder.toZoneDateTime().toLocalTime(),
            startDate = dto.startDate.toZoneDateTime()
        )
    }
}

fun Habit.toDto() : HabitResponse {
    val dto = HabitDto(
        name = name,
        frequency = frequency.map { it.value },
        completedDates = completedDates.map { it.toZoneDateTime().toTimeStamp() },
        reminder = reminder.toZoneDateTime().toTimeStamp(),
        startDate = startDate.toStartOfDateTimestamp()
    )

    return mapOf(id to dto)
}

fun Habit.toSyncEntity() : HabitSyncEntity {
    return HabitSyncEntity(id = id)
}