package com.example.data

import com.example.domain.Factura

class GetFacturasUseCase {

    private val repository = FacturaRepository()
    suspend operator fun invoke(): List<Factura>? {
        return repository.getAllFacturas()
    }
}