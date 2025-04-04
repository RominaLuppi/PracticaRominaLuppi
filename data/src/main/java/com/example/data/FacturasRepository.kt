package com.example.data

import com.example.practicarominaluppi.model.Factura

interface FacturasRepository {

    suspend fun getFacturas(): List<Factura>
}

class DefaultFacturasRepository(
    private val facturasApiService: FacturasApiService,
    private val facturasDao: FacturasDao) : FacturasRepository{

        override suspend fun getFacturas(): List<Factura>{
            return try{
                val facturas = facturasApiService.getFacturas()
                facturasDao.insertAll(facturas)
                facturas
            }catch (e: Exception){
                facturasDao.getAllFacturas()
            }
        }
}