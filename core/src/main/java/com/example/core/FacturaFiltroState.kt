package com.example.core

data class FacturaFiltroState(
    val fechaDesde: String,
    val fechaHasta: String,
    val importeMin: Double,
    val importeMax: Double,
    val estado: List<String>,
    val succes: Boolean
)
