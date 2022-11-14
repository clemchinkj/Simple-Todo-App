package com.deccovers.todomvvm.di

import android.content.Context
import androidx.room.Room
import com.deccovers.todomvvm.data.local.TodoDatabase
import com.deccovers.todomvvm.util.Constants.TODO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideTodoDao(@ApplicationContext context: Context) = Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            TODO_DATABASE
        ).build().todoDao
}