package com.example.data

import com.example.domain.Factura
import retrofit2.Response
import retrofit2.http.GET

interface FacturasApiClient {
   @GET("/facturas")

//  suspend fun getAllFacturas(): Response<List<Factura>>
//    suspend fun getAllFacturas(): Response<FacturaResponse>

   suspend fun getAllFacturas(): FacturaResponse

}