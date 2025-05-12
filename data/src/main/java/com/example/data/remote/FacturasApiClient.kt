package com.example.data.remote


import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.data.model.FacturaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FacturasApiClient {

   @Mock
   @MockResponse(body = "mock_facturas.json")
   @GET("facturas")
   suspend fun getAllFacturas(): FacturaResponse

//   @GET("facturas")
//
//   suspend fun getAllFacturas(): FacturaResponse

   @GET("facturas")
   suspend fun getFacturasFiltradas(
      @Query("fechaDesde") fechaDesde: String,
      @Query("fechaHasta") fechaHasta: String,
      @Query("importeMin") importeMin: Double,
      @Query("importeMax") importeMax: Double,
      @Query("estado") estado: List<String>
   ): FacturaResponse

//   @Mock
//   @MockResponse(body = "mock_facturas.json")
//   @GET("facturas")
//   suspend fun getMockFacturas(): FacturaResponse


}