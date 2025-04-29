package com.example.data.model

import com.google.gson.annotations.SerializedName

data class FacturaModel(
    @SerializedName ("descEstado") val estado: String,
    @SerializedName("importeOrdenacion") val importe: Double,
    @SerializedName("fecha") val fecha: String
)
