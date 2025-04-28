package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entities.FacturaEntity

@Dao
interface FacturasDao {

    @Query("SELECT * FROM facturas_table")
    suspend fun getAllFacturas(): List<FacturaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //inserta todas las facturas, si ya existe la reemplaza
    suspend fun insertAll(facturas: List<FacturaEntity>)
}