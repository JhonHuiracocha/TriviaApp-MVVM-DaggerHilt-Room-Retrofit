package com.multi.trivia.di

import android.app.Application
import androidx.room.Room
import com.multi.trivia.data.database.TriviaDatabase
import com.multi.trivia.data.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideTriviaDatabase(application: Application): TriviaDatabase {
        return Room
            .databaseBuilder(application, TriviaDatabase::class.java, "trivia.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(triviaDatabase: TriviaDatabase): UserDao {
        return triviaDatabase.userDao()
    }

}