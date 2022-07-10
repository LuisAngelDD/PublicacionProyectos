package com.example.postproyects.data.model.proyects

import com.google.gson.annotations.SerializedName

data class PostProyect(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val desc: String,
    @SerializedName("type") val type: String,
)
