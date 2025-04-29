package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.FacturaEntity

@Database(entities = [FacturaEntity::class], version = 1)

abstract class FacturaDatabase : RoomDatabase(){
    abstract fun getFacturasDao(): FacturasDao
}