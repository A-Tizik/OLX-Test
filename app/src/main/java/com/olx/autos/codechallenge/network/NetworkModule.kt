package com.olx.autos.codechallenge.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.olx.autos.codechallenge.network.api.OlxApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Module that provides a Retrofit instance at Application level component
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    /**
     * Retrofit service itself is provided as a Singleton inside the component
     * because we only need one retrofit instance for the app
     */
    @Provides
    @Singleton
    fun provideOlxService(): OlxApiService {
        val jsonConverter = Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(MediaType.get("application/json"))

        return Retrofit.Builder()
            .baseUrl("https://storage.googleapis.com/code-challenge/")
            .addConverterFactory(jsonConverter)
            .build()
            .create(OlxApiService::class.java)
    }

}