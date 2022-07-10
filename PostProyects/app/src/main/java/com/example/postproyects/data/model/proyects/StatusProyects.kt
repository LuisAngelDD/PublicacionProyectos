package com.example.postproyects.data.model.proyects

import com.google.gson.annotations.SerializedName

data class StatusProyects(
    @SerializedName("_id") val id: String,
    @SerializedName("statusProyect") val status: String,
)
