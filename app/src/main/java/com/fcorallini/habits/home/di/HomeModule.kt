package com.fcorallini.habits.home.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.fcorallini.habits.home.data.alarm.AlarmHandlerImpl
import com.fcorallini.habits.home.data.local.HomeDao
import com.fcorallini.habits.home.data.local.HomeDatabase
import com.fcorallini.habits.home.data.local.typeconverter.HomeTypeConverter
import com.fcorallini.habits.home.data.remote.HomeApi
import com.fcorallini.habits.home.data.repository.HomeRepositoryImpl
import com.fcorallini.habits.home.domain.alarm.AlarmHandler
import com.fcorallini.habits.home.domain.repository.HomeRepository
import com.squareup.moshi.Moshi
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

}
