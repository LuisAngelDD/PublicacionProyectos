package com.example.postproyects.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.postproyects.data.database.dao.UserDAO
import com.example.postproyects.data.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase:RoomDatabase() {
    abstract fun UserDao():UserDAO
}