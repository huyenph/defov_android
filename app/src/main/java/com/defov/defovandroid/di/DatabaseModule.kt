package com.defov.defovandroid.di

import android.content.Context
import androidx.room.Room
import com.defov.defovandroid.data.local.persistence.AppDao
import com.defov.defovandroid.data.local.persistence.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database.db").build()

    @Provides
    fun provideDBDao(database: AppDatabase): AppDao = database.appDao()
}