package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.Factura

@Dao
interface FacturasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //inserta todas las facturas, si ya existe la reemplaza
    suspend fun insertAll(facturas: List<Factura>) //obtiene todas las facturas de la BD

    @Query("SELECT * FROM EntidadFactura")
    suspend fun getAllFacturas(): List<Factura>
}