package com.example.data.repository

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
    private val apiClient: FacturasApiClient, //retrofit
//    private val mockApiClient: FacturasApiClient, //retromock
    private val facturasDao: FacturasDao    //room

) : FacturaRepository {

    override suspend fun getAllFacturas(): List<Factura>? {
        //intentar obtener desde la BD
        val facturasFromDb = facturasDao.getAllFacturas()
        //si hay facturas en la BD las devuelve
        if (facturasFromDb.isNotEmpty()){
            return facturasFromDb.map { it.toDomain() }
            }else {
            //si no hay facturas en la BD se obtienen de la api usando Retrofit o Retromock
//                val apiClient = if (MockConfig.mockActive) mockApiClient else apiClient
                val facturaResponse = apiClient.getAllFacturas()

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
            val facturaFromApi = apiClient.getFacturasFiltradas(
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

