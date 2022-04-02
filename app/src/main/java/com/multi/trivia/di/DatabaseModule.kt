package com.multi.trivia.di

import android.content.Context
import androidx.room.Room
import com.multi.trivia.data.database.TriviaDatabase
import com.multi.trivia.data.database.dao.ScoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideTriviaDatabase(@ApplicationContext context: Context): TriviaDatabase {
        return Room
            .databaseBuilder(context, TriviaDatabase::class.java, "trivia.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideScoreDao(triviaDatabase: TriviaDatabase): ScoreDao {
        return triviaDatabase.scoreDao()
    }

}