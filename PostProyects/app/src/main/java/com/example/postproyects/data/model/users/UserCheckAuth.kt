package com.example.postproyects.data.model.users

import com.google.gson.annotations.SerializedName

data class UserCheckAuth(
    @SerializedName("email") val correoElectronico: String,@SerializedName("optSend") val otp: String
)
