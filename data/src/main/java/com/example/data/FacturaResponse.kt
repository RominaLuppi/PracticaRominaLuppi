package com.example.data

import com.example.domain.Factura
import com.google.gson.annotations.SerializedName

//objeto completo que viene del servidor (Json)

data class FacturaResponse(
    @SerializedName("numFacturas") val numFacturas: Int,
    @SerializedName("facturas") val facturas: List<Factura>
)
