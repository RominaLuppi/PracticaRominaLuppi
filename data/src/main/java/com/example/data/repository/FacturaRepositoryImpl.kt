package com.example.data.repository

import android.util.Log
import com.example.data.RetrofitRepository
import com.example.data.RetromockRepository
import com.example.data.database.FacturasDao
import com.example.data.di.MockConfig
import com.example.data.remote.FacturasApiClient
import com.example.domain.Factura
import com.example.domain.repository.FacturaRepository
import com.example.data.mappers.toDomain
import com.example.data.mappers.toEntity
import com.example.domain.FacturaFiltroState
import javax.inject.Inject

class FacturaRepositoryImpl @Inject constructor(
    @RetrofitRepository private val retrofitClient: FacturasApiClient, //retrofit
    @RetromockRepository private val mockClient: FacturasApiClient, //retromock
    private val facturasDao: FacturasDao    //room

) : FacturaRepository {

    override suspend fun getAllFacturas(): List<Factura>? {
        //intentar obtener desde la BD
        val facturasFromDb = facturasDao.getAllFacturas()
        //si hay facturas en la BD las devuelve
        if (facturasFromDb.isNotEmpty()){
            return facturasFromDb.map { it.toDomain() }
        } else {
            //si no hay facturas en la BD se obtienen de la api usando Retrofit o Retromock
            val selectedClient = if (MockConfig.mockActive) mockClient else retrofitClient
//            Log.d("FacturaRepository", "Selected client: ${if (MockConfig.mockActive) "Mock" else "Retrofit"}")

            val facturaResponse = selectedClient.getAllFacturas()
//            Log.d("FacturaRepository", "Factura response: ${facturaResponse}")

            //se convierten las facturas dto a entidades para la DB
            val facturaEntityList = facturaResponse.facturas.map { it.toEntity() }

            //se guardan en la BD
            facturasDao.insertAll(facturaEntityList)

            //se convierten las entidades de Room a objetos de dominio para la UI
            return facturaEntityList.map { it.toDomain() }
        }
    }

    //obtener las facturas filtradas
    override suspend fun getFacturasFiltradas(filtro: FacturaFiltroState): List<Factura>? {
        //obtener las facturas filtradas desde la bd
        val factutasFromDb = facturasDao.getFacturasFiltradas(
            fechaDesde = filtro.fechaDesde,
            fechaHasta = filtro.fechaHasta,
            importeMin = filtro.importeMin,
            importeMax = filtro.importeMax,
            estado = filtro.estado
        )
        if (factutasFromDb.isNotEmpty()) {
            return factutasFromDb.map { it.toDomain() }
        } else {
            //si no hay facturas en la bd, se obtienen de la Api
            val facturaFromApi = retrofitClient.getFacturasFiltradas(
                fechaDesde = filtro.fechaDesde,
                fechaHasta = filtro.fechaHasta,
                importeMin = filtro.importeMin,
                importeMax = filtro.importeMax,
                estado = filtro.estado
            )
            val facturaEntityList = facturaFromApi.facturas.map { it.toEntity() }
            facturasDao.insertAll(facturaEntityList)
            return facturaEntityList.map { it.toDomain() }
        }
    }

}

