package com.example.postproyects.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_user_data_entity")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "jwt") val token: String,
)