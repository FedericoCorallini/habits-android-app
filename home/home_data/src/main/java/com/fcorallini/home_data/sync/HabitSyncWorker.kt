package com.fcorallini.home_data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fcorallini.home_data.local.HomeDao
import com.fcorallini.home_data.mapper.toDomain
import com.fcorallini.home_data.mapper.toDto
import com.fcorallini.home_data.mapper.toSyncEntity
import com.fcorallini.home_data.remote.HomeApi
import com.fcorallini.home_data.remote.util.resultOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

@HiltWorker
class HabitSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val api: HomeApi,
    private val dao: HomeDao
) : CoroutineWorker(context, workerParameters) {

    private suspend fun sync(id: String) {
        val habit = dao.getHabitById(id).toDomain()
        resultOf {
            api.insertHabit(habit.toDto())
        }.onSuccess {
            dao.deleteHabitSync(habit.toSyncEntity())
        }.onFailure {
            throw it
        }
    }

    override suspend fun doWork(): Result {
        if (runAttemptCount >= 3) {
            return Result.failure()
        }

        val items = dao.getSyncHabits()

        return try {
            supervisorScope {
                val jobs = items.map { item -> async { sync(item.id) } }
                jobs.awaitAll()
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}