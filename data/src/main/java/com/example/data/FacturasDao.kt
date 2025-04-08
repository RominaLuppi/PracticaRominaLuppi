package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FacturasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //inserta todas las facturas, si ya existe la reemplaza
    suspend fun insertAll(facturas: List<EntidadFactura>) //obtiene todas las facturas de la BD

    @Query("SELECT * FROM EntidadFactura")
    suspend fun getAllFacturas(): List<EntidadFactura>
}