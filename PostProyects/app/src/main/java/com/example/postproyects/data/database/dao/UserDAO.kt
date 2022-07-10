package com.example.postproyects.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.postproyects.data.database.entities.UserEntity

@Dao
interface UserDAO {
    @Query("Select * From login_user_data_entity")
    suspend fun getUserData():UserEntity
    @Insert()
    suspend fun insertUserData (dataUser:UserEntity)
    @Query("Delete From login_user_data_entity")
    suspend fun deleteDataUser()
}