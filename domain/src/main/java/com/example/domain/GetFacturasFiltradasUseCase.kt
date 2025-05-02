package com.example.domain

import com.example.domain.repository.FacturaRepository


class GetFacturasFiltradasUseCase(private val repository: FacturaRepository) {
    suspend operator fun invoke(filtro: FacturaFiltroState): List<Factura> {
        return repository.getFacturasFiltradas(filtro) ?: emptyList()
    }
}
