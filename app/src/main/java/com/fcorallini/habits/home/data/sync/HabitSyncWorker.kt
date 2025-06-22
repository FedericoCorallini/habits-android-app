package com.fcorallini.habits.home.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fcorallini.habits.home.data.local.HomeDao
import com.fcorallini.habits.home.data.mapper.toDomain
import com.fcorallini.habits.home.data.mapper.toDto
import com.fcorallini.habits.home.data.mapper.toSyncEntity
import com.fcorallini.habits.home.data.remote.HomeApi
import com.fcorallini.habits.home.data.remote.util.resultOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltWorker
class HabitSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val api: HomeApi,
    private val dao: HomeDao
) : CoroutineWorker(context, workerParameters){

    private suspend fun sync(id : String) {
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
        val items = dao.getSyncHabits()
        if(runAttemptCount >= 3) {
            return Result.failure()
        }
        return try {
            supervisorScope {
                val jobs = items.map { item -> async { sync(item.id) } }
                jobs.joinAll()
            }
            Result.success()
        } catch (e : Exception) {
            Result.retry()
        }
    }
}