package com.example.data.di

import android.content.Context
import co.infinum.retromock.Retromock
import com.example.data.RetrofitRepository
import com.example.data.RetromockRepository
import com.example.data.remote.FacturasApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//modulos para proveer hilt
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://20291bf9-f1e2-48e8-999c-cf7e02d4f1ee.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    @Singleton
    @Provides
    fun provideRetromock(
        retrofit: Retrofit,
        @ApplicationContext context: Context): Retromock {
        return Retromock.Builder()
            .retrofit(retrofit)
            .defaultBodyFactory(context.assets::open)
            .build()
    }

    @Singleton
    @Provides
    @RetrofitRepository
    fun provideFacturasApiClient(retrofit: Retrofit): FacturasApiClient {
        return retrofit.create(FacturasApiClient::class.java)
    }

    @Singleton
    @Provides
    @RetromockRepository
    fun provideMockFacturasApiClient(retromock: Retromock): FacturasApiClient {
        return retromock.create(FacturasApiClient::class.java)
    }

}