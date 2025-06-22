package com.fcorallini.habits.home.domain.home.usecases

import com.fcorallini.habits.home.domain.model.Habit
import com.fcorallini.habits.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import javax.inject.Inject

class GetHabitsForDateUseCase @Inject constructor(
    private val homeRepository: HomeRepository
)  {
    operator fun invoke(date : ZonedDateTime) : Flow<List<Habit>> {
        return homeRepository.getAllHabitsForSelectedDate(date).map {
            it.filter { it.frequency.contains(date.dayOfWeek) }
        }.distinctUntilChanged()
    }
}