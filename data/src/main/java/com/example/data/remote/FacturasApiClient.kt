package com.example.data.remote


import com.example.data.model.FacturaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FacturasApiClient {
   @GET("facturas")

   suspend fun getAllFacturas(): FacturaResponse

   @GET("facturas")
   suspend fun getFacturasFiltradas(
      @Query("fechaDesde") fechaDesde: String,
      @Query("fechaHasta") fechaHasta: String,
      @Query("importeMin") importeMin: Double,
      @Query("importeMax") importeMax: Double,
      @Query("estado") estado: List<String>
   ): FacturaResponse
}