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
object RepositoryModule{
    @Provides
    @Singleton
    @RetrofitRepository
    fun provideFacturaRepository(
        @RetrofitRepository retrofitClient: FacturasApiClient,
        facturasDao: FacturasDao
    ) : FacturaRepository{
        return FacturaRepositoryImpl(
            apiClient = retrofitClient,
            facturasDao = facturasDao )
    }

    @Provides
    @Singleton
    @RetromockRepository
    fun provideMockFacturaRepository(
        @RetromockRepository mockApiClient: FacturasApiClient,
        facturasDao: FacturasDao
    ): FacturaRepository {
        return FacturaRepositoryImpl(
            apiClient = mockApiClient,
            facturasDao = facturasDao
        )
    }
}


