package com.example.data

//configuracion de Retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://20291bf9-f1e2-48e8-999c-cf7e02d4f1ee.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}