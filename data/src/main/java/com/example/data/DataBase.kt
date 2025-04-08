package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [EntidadFactura::class], version = 1) //entidades de la BD
abstract class DataBase : RoomDatabase(){
    abstract fun facturasDao() : FacturasDao //metodo abstracto que devuelve el DAO para interactuar con la BD
}