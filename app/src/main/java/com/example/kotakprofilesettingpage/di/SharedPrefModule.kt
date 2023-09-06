package com.example.kotakprofilesettingpage.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefModule {
    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context):SharedPreferences{
        return context.getSharedPreferences("MY_SETTINGS",Context.MODE_PRIVATE)
    }
}