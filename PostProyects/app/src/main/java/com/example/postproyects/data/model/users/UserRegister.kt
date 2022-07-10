package com.example.postproyects.data.model.users

import com.google.gson.annotations.SerializedName

data class UserRegister(
    @SerializedName("correo") val correoElectronico: String,
    @SerializedName("password") val pass: String,
)
