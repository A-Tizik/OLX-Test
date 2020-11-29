package com.olx.autos.codechallenge.network.api

import com.olx.autos.codechallenge.network.models.CarsResponse
import retrofit2.http.GET

interface OlxApiService {
    @GET("cars.json")
    suspend fun getCarList():CarsResponse
}