package com.example.data.di

import com.example.data.RetrofitRepository
import com.example.data.RetromockRepository
import com.example.data.database.FacturasDao
import com.example.data.remote.FacturasApiClient
import com.example.data.repository.FacturaRepositoryImpl
import com.example.domain.repository.FacturaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @RetrofitRepository
    @Provides
    @Singleton
    fun provideRetrofitFacturaRepository(
        @RetrofitRepository retrofitClient: FacturasApiClient,
        @RetromockRepository mockApiClient: FacturasApiClient,
        facturasDao: FacturasDao
    ): FacturaRepository {
        return FacturaRepositoryImpl(
            retrofitClient = retrofitClient,
            mockClient = mockApiClient, //o una instancia nula predeterminada
            facturasDao = facturasDao
        )
    }

    @Provides
    @Singleton
    @RetromockRepository
    fun provideRetromockFacturaRepository(
        @RetrofitRepository retrofitClient: FacturasApiClient,
        @RetromockRepository mockApiClient: FacturasApiClient,
        facturasDao: FacturasDao
    ): FacturaRepository {
        return FacturaRepositoryImpl(
            retrofitClient = retrofitClient, //o una instancia nula predeterminada
            mockClient = mockApiClient,
            facturasDao = facturasDao
        )
    }

    @Provides
    @Singleton
    fun provideFacturaRepository(
        @RetrofitRepository repository: FacturaRepository //la predeterminada retrofit o retromock
    ): FacturaRepository {
        return repository
    }
}




