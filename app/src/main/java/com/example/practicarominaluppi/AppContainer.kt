package com.example.practicarominaluppi

import android.content.Context
import androidx.room.Room
import com.example.data.DataBase
import com.example.data.DefaultFacturasRepository
import com.example.data.FacturasApiService
import com.example.data.FacturasDao
import com.example.data.FacturasRepository
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

/** interfaz que actúa como contenedor de las dependencias */
interface AppContainer {
    val facturasRepository: FacturasRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer{
    private val BASE_URL= "https://viewnextandroid.wiremockapi.cloud/"

    //configuración de Retrofit
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: FacturasApiService by lazy{
        retrofit.create(FacturasApiService::class.java)
    }

    //configuración de Room
    private val db: DataBase by lazy{
        Room.databaseBuilder(
            context.applicationContext,
            DataBase::class.java, "facturas_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
    private val facturasDao: FacturasDao by lazy{
        db.facturasDao()
    }

    //repositorio con retrofit y room
    override val facturasRepository: FacturasRepository by lazy{
        DefaultFacturasRepository(retrofitService, facturasDao)
    }
}