package com.example.data

@Database(entities = [Factura::class], version = 1) //entidades de la BD
abstract class DataBase : RoomDatabase(){
    abstract fun facturasDao() : FacturasDao //metodo abstracto que devuelve el DAO para interactuar con la BD
}