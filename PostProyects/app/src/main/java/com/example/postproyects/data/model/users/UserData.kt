package com.example.postproyects.data.model.users

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("correo") val email: String,
    @SerializedName("ocupacion") val ocupacion: String,
    @SerializedName("about") val about: String,
    @SerializedName("public_id") val id: String?,
    @SerializedName("secure_url") val url: String?,
)
