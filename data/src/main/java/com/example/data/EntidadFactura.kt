package com.example.data

@Entity
data class EntidadFactura(
    @PrimaryKey(autoGenerate = true) val id: Int
    @ColumnInfo(name = "descEstado") val estado: String,
    @ColumnInfo(name = "importeOrdenacion") val importe: Double,
    @ColumnInfo(name = "fecha") val fecha: String
)
