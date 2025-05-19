package com.example.data.mappers

import com.example.data.model.FacturaEntity
import com.example.data.remote.FacturaDto
import com.example.domain.Factura



fun Factura.toDomain() = Factura(descEstado, importeOrdenacion, fecha, kwh)

//se convierte la FacturaDto (api) a FacturaEntity (BD) para almacenar en room
fun FacturaDto.toEntity() = FacturaEntity(
    estado = this.descEstado,
    importe = this.importeOrdenacion,
    fecha = this.fecha,
    kwh = this.kwh

)

//se convierte FacturaDto (api) a Factura para usar en la capa de dominio y UI
fun FacturaDto.toDomain() = Factura(
    descEstado = this.descEstado,
    importeOrdenacion = this.importeOrdenacion,
    fecha = this.fecha,
    kwh = this.kwh

)

// se convierte FacturaEntity (BD) a Factura (dominio)
fun FacturaEntity.toDomain() = Factura(
    descEstado = this.estado,
    importeOrdenacion = this.importe,
    fecha = this.fecha,
    kwh = this.kwh
)