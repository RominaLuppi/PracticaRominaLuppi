package com.example.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class EntidadFactura(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "descEstado_db") val estado: String,
    @ColumnInfo(name = "importeOrdenacion_db") val importe: Double,
    @ColumnInfo(name = "fecha_db") val fecha: String
)
