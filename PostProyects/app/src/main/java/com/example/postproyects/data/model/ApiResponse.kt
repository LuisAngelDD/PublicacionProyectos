package com.example.postproyects.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("val")val code: Boolean,
    @SerializedName("message")val mensaje: String,
)
