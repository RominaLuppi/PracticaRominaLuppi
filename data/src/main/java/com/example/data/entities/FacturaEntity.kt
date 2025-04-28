package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facturas_table")
data class FacturaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "descEstado_db") val estado: String,
    @ColumnInfo(name = "importeOrdenacion_db") val importe: Double,
    @ColumnInfo(name = "fecha_db") val fecha: String
)