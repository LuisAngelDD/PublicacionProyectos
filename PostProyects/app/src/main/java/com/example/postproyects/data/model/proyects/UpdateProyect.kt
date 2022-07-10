package com.example.postproyects.data.model.proyects

import com.google.gson.annotations.SerializedName

data class UpdateProyect(@SerializedName("nombreProyecto") val nombre: String,
                         @SerializedName("descripcionProyecto") val desc: String,
                         @SerializedName("statusProyecto") val status: String,)
