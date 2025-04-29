package com.example.data.di

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
    fun provideFacturaRepository(
        apiClient: FacturasApiClient,
        dao: FacturasDao
    ) : FacturaRepository{
        return FacturaRepositoryImpl(apiClient, dao)
    }
}
