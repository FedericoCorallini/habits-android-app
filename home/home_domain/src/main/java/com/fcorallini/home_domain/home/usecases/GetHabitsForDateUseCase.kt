package com.fcorallini.home_domain.home.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import com.fcorallini.home_domain.model.Habit
import com.fcorallini.home_domain.repository.HomeRepository

class GetHabitsForDateUseCase(
    private val homeRepository: HomeRepository
)  {
    operator fun invoke(date : ZonedDateTime) : Flow<List<Habit>> {
        return homeRepository.getAllHabitsForSelectedDate(date).map {
            it.filter { it.frequency.contains(date.dayOfWeek) }
        }.distinctUntilChanged()
    }
}