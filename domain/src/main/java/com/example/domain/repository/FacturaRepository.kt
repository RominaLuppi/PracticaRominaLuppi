package com.example.domain.repository

import com.example.domain.Factura
import com.example.domain.FacturaFiltroState

interface FacturaRepository {
    //se obtienen todas las facturas disponibles
    suspend fun getAllFacturas(): List<Factura>?

    //se obtienen las facturas filtradas
    suspend fun getFacturasFiltradas(filtro: FacturaFiltroState): List<Factura>?
}