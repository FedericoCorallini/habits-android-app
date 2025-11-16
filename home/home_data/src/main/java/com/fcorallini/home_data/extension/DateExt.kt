package com.fcorallini.home_data.extension

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun ZonedDateTime.toStartOfDateTimestamp() : Long {
    return truncatedTo(ChronoUnit.DAYS).toEpochSecond() * 1000
}

fun Long.toZoneDateTime() : ZonedDateTime {
    return ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
}

fun ZonedDateTime.toTimeStamp() : Long {
    return this.toInstant().toEpochMilli()
}

fun LocalDate.toZoneDateTime() : ZonedDateTime {
    return this.atStartOfDay(ZoneId.systemDefault())
}

fun LocalTime.toZoneDateTime() : ZonedDateTime {
    return this.atDate(LocalDate.now()).atZone(ZoneId.systemDefault())
}