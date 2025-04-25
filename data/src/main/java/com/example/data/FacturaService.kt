package com.example.data

import com.example.domain.Factura
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.create


//para devolver un servicio de internet

class FacturaService {

    private  val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getFacturas(): List<Factura>{
        return withContext(Dispatchers.IO) {
//            val response: Response<List<Factura>> =
//                retrofit.create(FacturasApiClient::class.java).getAllFacturas()
//                response.body() ?: emptyList()

            val response: FacturaResponse =
                retrofit.create(FacturasApiClient::class.java).getAllFacturas()
            response.facturas

        }
    }

}