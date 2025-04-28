package com.example.data

import com.example.domain.Factura
import javax.inject.Inject

//para que me devuelva las facturas
class FacturaRepository @Inject constructor(private val api: FacturasApiClient,
    private val facturaProvider: FacturaProvider) {


//private val api = RetrofitHelper.getRetrofit().create(FacturasApiClient::class.java)

    suspend fun getAllFacturas(): List<Factura>{

        val response: FacturaResponse = api.getAllFacturas()
        facturaProvider.facturas = response.facturas
        return response.facturas

    }
}