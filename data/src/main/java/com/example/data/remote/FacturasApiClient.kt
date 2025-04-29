package com.example.data.remote

import com.example.data.model.FacturaResponse
import retrofit2.http.GET

interface FacturasApiClient {
   @GET("facturas")

   suspend fun getAllFacturas(): FacturaResponse

}