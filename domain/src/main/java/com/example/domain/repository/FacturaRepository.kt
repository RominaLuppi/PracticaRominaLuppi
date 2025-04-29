package com.example.domain.repository

import com.example.domain.Factura

interface FacturaRepository {
    suspend fun getAllFacturas(): List<Factura>?
}