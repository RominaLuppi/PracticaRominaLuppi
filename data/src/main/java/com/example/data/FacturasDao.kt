package com.example.data

@Dao
interface FacturasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //inserta todas las facturas, si ya existe la reemplaza
    suspend fun insertAll(facturas: List<Factura>) //obtiene todas las facturas de la BD

    @Query("SELECT * FROM facturas")
    suspend fun getAllFacturas(): List<Factura>
}