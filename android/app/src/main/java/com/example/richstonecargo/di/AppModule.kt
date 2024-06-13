package com.example.richstonecargo.di

import com.example.richstonecargo.common.Constants
import com.example.richstonecargo.data.remote.CargoBackendApi
import com.example.richstonecargo.data.repository.CargoRepositoryImpl
import com.example.richstonecargo.domain.repository.CargoRepository
import com.example.richstonecargo.network.DynamicInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        return GsonBuilder()
            .registerTypeAdapter(Date::class.java, JsonDeserializer { json, _, _ ->
                dateFormat.parse(json.asString)
            })
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(DynamicInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideCargoBackendApi(client: OkHttpClient, gson: Gson): CargoBackendApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BACKEND_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(CargoBackendApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCargoRepository(api: CargoBackendApi): CargoRepository {
        return CargoRepositoryImpl(api)
    }
}