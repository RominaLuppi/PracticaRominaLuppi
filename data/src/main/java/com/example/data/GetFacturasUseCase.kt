package com.example.data

import com.example.domain.Factura
import javax.inject.Inject

class GetFacturasUseCase @Inject constructor(private val repository : FacturaRepository){


    suspend operator fun invoke(): List<Factura>? {
        return repository.getAllFacturas()
    }
}