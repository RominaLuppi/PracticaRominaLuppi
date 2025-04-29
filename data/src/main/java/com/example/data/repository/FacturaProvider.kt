package com.example.data.repository

import com.example.domain.Factura
import javax.inject.Inject
import javax.inject.Singleton

//en la primera llamada al servidor se almacenan las facturas en FacturaProvider
@Singleton //para que mantengan una única instancia durante tod el proyecto
class FacturaProvider @Inject constructor(){
    var facturas: List<Factura> = emptyList()
}