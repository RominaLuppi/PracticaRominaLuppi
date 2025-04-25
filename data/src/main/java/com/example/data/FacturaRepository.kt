package com.example.data

import com.example.domain.Factura

//para que me devuelva las facturas
class FacturaRepository {

//    private val api = FacturaService()
private val api = RetrofitHelper.getRetrofit().create(FacturasApiClient::class.java)

    suspend fun getAllFacturas(): List<Factura>{
//        val response = api.getFacturas()
//        val response: List<Factura> = api.getFacturas()
        val response: FacturaResponse = api.getAllFacturas()
        FacturaProvider.facturas = response.facturas
        return response.facturas
//        FacturaProvider.facturas = response
//        return response
    }
}