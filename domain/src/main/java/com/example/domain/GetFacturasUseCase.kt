package com.example.domain

import com.example.domain.repository.FacturaRepository


class GetFacturasUseCase (private val repository : FacturaRepository){

    suspend operator fun invoke(): List<Factura>? {
        return repository.getAllFacturas()
    }
}