package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.FacturaEntity

@Dao
interface FacturasDao {

    @Query("SELECT * FROM facturas_table")
    suspend fun getAllFacturas(): List<FacturaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //inserta todas las facturas, si ya existe la reemplaza
    suspend fun insertAll(facturas: List<FacturaEntity>)

    //consulta para filtrar
    @Query("""
        SELECT * FROM facturas_table
        WHERE fecha_db >= :fechaDesde AND fecha_db <= :fechaHasta
        AND importeOrdenacion_db >= :importeMin AND importeOrdenacion_db <= :importeMax
        AND descEstado_db IN (:estado)        
    """)

    suspend fun getFacturasFiltradas(
        fechaDesde: String,
        fechaHasta: String,
        importeMin: Double,
        importeMax: Double,
        estado: List<String>
    ): List<FacturaEntity>


}