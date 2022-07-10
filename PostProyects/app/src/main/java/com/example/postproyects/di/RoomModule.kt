package com.example.postproyects.di

import android.content.Context
import androidx.room.Room
import com.example.postproyects.data.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context:Context) =
        Room.databaseBuilder(context,UserDatabase::class.java, Companion.USER_DATABASE_NAME).build()

    companion object {
        private const val USER_DATABASE_NAME = "data_user"
    }
    @Singleton
    @Provides
    fun provideUserDao(db:UserDatabase) = db.UserDao()
}