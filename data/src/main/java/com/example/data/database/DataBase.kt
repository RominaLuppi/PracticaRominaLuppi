package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.FacturaEntity

@Database(entities = [FacturaEntity::class], version = 1) //entidades de la BD
abstract class DataBase : RoomDatabase(){

    abstract fun getFacturasDao() : FacturasDao //metodo abstracto que devuelve el DAO para interactuar con la BD
}