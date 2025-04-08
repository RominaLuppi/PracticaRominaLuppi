package com.example.domain

import com.google.gson.annotations.SerializedName

data class Factura(

   @SerializedName("descEstado") val estado: String,
   @SerializedName("importeOrdenacion") val importe: Double,
   @SerializedName("fecha") val fecha: String

    )