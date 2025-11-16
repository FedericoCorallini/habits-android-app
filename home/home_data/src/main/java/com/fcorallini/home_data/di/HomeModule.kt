package com.fcorallini.home_data.di

import android.content.Context
import androidx.room.Insert
import androidx.room.Room
import androidx.work.WorkManager
import com.fcorallini.home_data.alarm.AlarmHandlerImpl
import com.fcorallini.home_data.local.HomeDao
import com.fcorallini.home_data.local.HomeDatabase
import com.fcorallini.home_data.local.typeconverter.HomeTypeConverter
import com.fcorallini.home_data.remote.HomeApi
import com.fcorallini.home_data.repository.HomeRepositoryImpl
import com.fcorallini.home_domain.alarm.AlarmHandler
import com.fcorallini.home_domain.detail.usecases.GetHabitByIdUseCase
import com.fcorallini.home_domain.detail.usecases.InsertHabitUseCase
import com.fcorallini.home_domain.home.usecases.CompleteHabitUseCase
import com.fcorallini.home_domain.home.usecases.GetHabitsForDateUseCase
import com.fcorallini.home_domain.home.usecases.SyncHabitsUseCase
import com.fcorallini.home_domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideDao(@ApplicationContext context: Context) : HomeDao {
        return Room.databaseBuilder(
            context = context,
            HomeDatabase::class.java,
            "habits_db"
        ).addTypeConverter(HomeTypeConverter()).build().dao
    }

    @Provides
    @Singleton
    fun provideHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            ).build()
    }

    @Provides
    @Singleton
    fun provideApi(httpClient: OkHttpClient) : HomeApi {
        return Retrofit.Builder()
            .baseUrl(HomeApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
            .create(HomeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        dao: HomeDao,
        api: HomeApi,
        alarmHandler: AlarmHandler,
        workManager: WorkManager
    ) : HomeRepository {
        return HomeRepositoryImpl(dao, api, alarmHandler, workManager)
    }

    @Provides
    @Singleton
    fun provideAlarmHandler(@ApplicationContext context: Context) : AlarmHandler {
        return AlarmHandlerImpl(context)
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context) : WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun providesGetHabitByIdUseCase(homeRepository: HomeRepository) : GetHabitByIdUseCase {
        return GetHabitByIdUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun providesInsertHabitUseCase(homeRepository: HomeRepository) : InsertHabitUseCase {
        return InsertHabitUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun providesCompleteHabitUseCase(homeRepository: HomeRepository) : CompleteHabitUseCase {
        return CompleteHabitUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun providesGetHabitsForDateUseCase(homeRepository: HomeRepository) : GetHabitsForDateUseCase {
        return GetHabitsForDateUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun providesSyncHabitsUseCase(homeRepository: HomeRepository) : SyncHabitsUseCase {
        return SyncHabitsUseCase(homeRepository)
    }
}
