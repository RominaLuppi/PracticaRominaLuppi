package com.example.data.model

import com.example.data.remote.FacturaDto
import com.example.domain.Factura
import com.google.gson.annotations.SerializedName

data class FacturaResponse(
    @SerializedName("numFacturas") val numFacturas: Int,
    @SerializedName("facturas") val facturas: List<FacturaDto>
)