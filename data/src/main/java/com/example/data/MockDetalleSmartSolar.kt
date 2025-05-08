package com.example.data

data class MockDetalleSmartSolar(
    var cau: String = "ES00002100000001994LJ1FA00",
    var estadoSolicitud: String = "No hemos recibido ninguna solicitud de autoconsumo",
    var tipoAutoconsumo: String = "Con excedentes y compensación individual - Consumo",
    var compensacion: String = "Precio PVPC",
    var potencia: String = "5kWp"
)
