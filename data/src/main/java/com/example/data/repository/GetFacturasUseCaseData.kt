package com.example.data.repository

import com.example.domain.Factura
import com.example.domain.repository.FacturaRepository
import javax.inject.Inject

class GetFacturasUseCaseData @Inject constructor(
    private val repository: FacturaRepository
) {
    suspend operator fun invoke(): List<Factura>? {
        return repository.getAllFacturas()
    }
}
