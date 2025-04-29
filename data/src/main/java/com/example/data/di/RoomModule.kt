package com.example.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.data.database.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    private val DATABASE_NAME = "factura_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DataBase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideFacturasDao(dataBase: DataBase) = dataBase.getFacturasDao()


}