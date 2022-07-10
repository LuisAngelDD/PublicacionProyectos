package com.example.postproyects.data.model.proyects

import com.google.gson.annotations.SerializedName

data class TypeProyects(
    @SerializedName("_id") val id: String,
    @SerializedName("nombreTipoProyecto") val tipo: String,
)
