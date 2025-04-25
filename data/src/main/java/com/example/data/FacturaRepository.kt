package com.example.data

import com.example.domain.Factura

//para que me devuelva las facturas
class FacturaRepository {

    private val api = FacturaService()

    suspend fun getAllFacturas(): List<Factura>{
//        val response = api.getFacturas()
        val response: List<Factura> = api.getFacturas()
        FacturaProvider.facturas = response
        return response
    }
}