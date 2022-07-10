package com.example.postproyects.data.model.proyects

import com.google.gson.annotations.SerializedName

data class Pr(@SerializedName("_id") val id: String,
              @SerializedName("nombreProyecto") val nombre: String,
              @SerializedName("authorNameProyecto") val author: String,
              @SerializedName("descripcionProyecto") val desc: String,
              @SerializedName("tipoProyecto") val tipo: String,
              @SerializedName("statusProyecto") val estado: String,)
