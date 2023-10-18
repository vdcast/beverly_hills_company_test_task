package com.example.beverly_hills_company_test_task.domain.di

import android.content.Context
import androidx.room.Room
import com.example.beverly_hills_company_test_task.data.db.AppDatabase
import com.example.beverly_hills_company_test_task.data.records.RecordDao
import com.example.beverly_hills_company_test_task.data.records.RecordRepos
import com.example.beverly_hills_company_test_task.data.records.RecordReposImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomRepositoriesModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .build()
    }

    @Singleton
    @Provides
    fun provideRecordDao(database: AppDatabase): RecordDao {
        return database.recordDao()
    }

    @Singleton
    @Provides
    fun provideRecordRepository(recordDao: RecordDao): RecordRepos = RecordReposImpl(recordDao)
}