package com.example.data

import com.example.domain.Factura
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FacturaProvider @Inject constructor(){
    var facturas: List<Factura> = emptyList()
}