package com.example.practicarominaluppi.di

import com.example.data.RetrofitRepository
import com.example.data.RetromockRepository
import com.example.domain.GetFacturasFiltradasUseCase
import com.example.domain.GetFacturasUseCase
import com.example.domain.repository.FacturaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    @Provides
    @Singleton
    fun provideGetFacturasUseCase(
        @RetrofitRepository repository: FacturaRepository)
    : GetFacturasUseCase{
        return GetFacturasUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGetFacturasFiltradasUseCase(
        @RetromockRepository repository: FacturaRepository)
    : GetFacturasFiltradasUseCase {
        return GetFacturasFiltradasUseCase(repository)
    }
}
