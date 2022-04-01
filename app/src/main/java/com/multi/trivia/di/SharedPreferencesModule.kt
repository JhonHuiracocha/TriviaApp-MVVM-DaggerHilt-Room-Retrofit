package com.multi.trivia.di

import android.content.Context
import android.content.SharedPreferences
import com.multi.trivia.data.model.User
import com.multi.trivia.utils.Constants.SESSION_MANAGER_NAME
import com.multi.trivia.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SESSION_MANAGER_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSessionManager(preferences: SharedPreferences) = SessionManager(preferences)

}