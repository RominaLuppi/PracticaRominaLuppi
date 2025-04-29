package com.example.data.repository

import com.example.data.database.FacturasDao
import com.example.data.remote.FacturasApiClient
import com.example.domain.Factura
import com.example.domain.repository.FacturaRepository
import com.example.data.mappers.toDomain
import com.example.data.mappers.toEntity
import javax.inject.Inject

class FacturaRepositoryImpl @Inject constructor(
    private val apiClient: FacturasApiClient, //retrofit
    private val facturasDao: FacturasDao    //room
) : FacturaRepository {

    //obtener las facturas dede la api
    override suspend fun getAllFacturas(): List<Factura>? {
        //intentar obtener desde la BD
        val facturasFromDb = facturasDao.getAllFacturas()
        //si hay facturas en la BD las devuelve
        if (facturasFromDb.isNotEmpty()){
            return facturasFromDb.map { it.toDomain() }
            }
        //si no hay facturas en la BD se obtienen de Retrofit
        //se obtienen las facturas de la api usando Retrofit
        val facturaResponse = apiClient.getAllFacturas()

        //se convierten las facturas dto a entidades para la DB
        val facturaEntityList = facturaResponse.facturas.map { it.toEntity() }

        //se guardan en la BD
        facturasDao.insertAll(facturaEntityList)

        //se convierten las entidades de Room a objetos de dominio para la UI
        return facturaEntityList.map { it.toDomain() }
    }

}

