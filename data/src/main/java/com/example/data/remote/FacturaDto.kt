package com.example.data.remote

import com.google.gson.annotations.SerializedName

//modelo que se recibe desde la api
data class FacturaDto(
        @SerializedName("descEstado") val descEstado: String,
        @SerializedName("importeOrdenacion") val importeOrdenacion: Double,
        @SerializedName("fecha") val fecha: String
    )

