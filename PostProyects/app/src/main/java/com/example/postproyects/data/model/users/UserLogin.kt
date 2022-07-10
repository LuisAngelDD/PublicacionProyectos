package com.example.postproyects.data.model.users

import com.google.gson.annotations.SerializedName

data class UserLogin (
        @SerializedName("email") val correoElectronico: String,
        @SerializedName("password") val pase: String,
)