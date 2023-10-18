package com.example.beverly_hills_company_test_task.domain.di

import com.example.beverly_hills_company_test_task.data.get.FacebookRepos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FacebookModule {

    @Provides
    fun provideFacebookRepos(): FacebookRepos {
        return FacebookRepos()
    }
}