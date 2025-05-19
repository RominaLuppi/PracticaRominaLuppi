package com.example.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//modelo de la BD (room)
@Entity(tableName = "facturas_table")

data class FacturaEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "descEstado_db") val estado: String,
    @ColumnInfo(name = "importeOrdenacion_db") val importe: Double,
    @ColumnInfo(name = "fecha_db") val fecha: String,
    @ColumnInfo(name = "kwh_db") val kwh: Double
)