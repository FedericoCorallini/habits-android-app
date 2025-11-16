package com.fcorallini.home_data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.fcorallini.home_data.extension.toStartOfDateTimestamp
import com.fcorallini.home_data.local.HomeDao
import com.fcorallini.home_data.mapper.toDomain
import com.fcorallini.home_data.mapper.toDto
import com.fcorallini.home_data.mapper.toEntity
import com.fcorallini.home_data.mapper.toSyncEntity
import com.fcorallini.home_data.remote.HomeApi
import com.fcorallini.home_data.remote.util.resultOf
import com.fcorallini.home_data.sync.HabitSyncWorker
import com.fcorallini.home_domain.alarm.AlarmHandler
import com.fcorallini.home_domain.model.Habit
import com.fcorallini.home_domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.time.Duration
import java.time.ZonedDateTime

class HomeRepositoryImpl(
    private val dao: HomeDao,
    private val api: HomeApi,
    private val alarmHandler: AlarmHandler,
    private val workManager: WorkManager
) : HomeRepository {

    private fun getHabitsFromApi() : Flow<List<Habit>> {
        return flow {
            resultOf {
                val habits = api.getAllHabits().toDomain()
                insertHabits(habits)
            }
            emit(emptyList<Habit>())
        }.onStart {
            emptyList<Habit>()
        }
    }

    private suspend fun insertHabits(habits : List<Habit>) {
        habits.forEach {
            handleAlarm(it)
            dao.insertHabit(it.toEntity())
        }
    }

    private suspend fun handleAlarm(habit: Habit) {
        try {
            val previous = dao.getHabitById(habit.id).toDomain()
            alarmHandler.cancel(previous)
        } catch (e : Exception) { /* habit doesn't exist */}
        alarmHandler.setRecurringAlarm(habit)
    }

    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        val localFlow = dao.getHabitsForSelectedDate(date.toStartOfDateTimestamp())
            .map { it.map { it.toDomain() } }
        val apiFlow = getHabitsFromApi()
        return localFlow.combine(apiFlow) { db, _ -> db }
    }

    override suspend fun insertHabit(habit: Habit) {
        handleAlarm(habit)
        dao.insertHabit(habit.toEntity())
        resultOf {
            api.insertHabit(habit.toDto())
        }.onFailure {
            dao.insertSyncHabit(habit.toSyncEntity())
        }
    }

    override suspend fun getHabitById(id: String): Habit {
        return dao.getHabitById(id).toDomain()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun syncHabits() {
        val worker = OneTimeWorkRequestBuilder<HabitSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5)).build()

        workManager.beginUniqueWork("sync_habit_id", existingWorkPolicy = ExistingWorkPolicy.REPLACE, worker).enqueue()
    }
}