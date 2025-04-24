package com.example.data

import com.example.domain.Factura

class FacturaRepository {

    private val api = FacturaService()

    suspend fun getAllFacturas(): List<Factura>{
        val response = api.getFacturas()
        FacturaProvider.facturas = response
        return response
    }
}