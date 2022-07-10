package com.example.postproyects.data.model.users

import com.google.gson.annotations.SerializedName

data class UserToken (
    @SerializedName("jwt") val token: String,
)