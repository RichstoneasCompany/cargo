package com.example.richstonecargo.di

import com.example.richstonecargo.common.Constants
import com.example.richstonecargo.data.remote.CargoBackendApi
import com.example.richstonecargo.data.repository.MockedCargoRepositoryImpl
import com.example.richstonecargo.domain.repository.CargoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCargoBackendApi(): CargoBackendApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BACKEND_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CargoBackendApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCargoRepository(api: CargoBackendApi): CargoRepository {
        return MockedCargoRepositoryImpl(api)
    }
}