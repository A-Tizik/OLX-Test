package com.olx.autos.codechallenge

import com.olx.autos.codechallenge.network.api.OlxApiService
import com.olx.autos.codechallenge.network.models.CarsResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Simple fake service for testing anything with a network dependency
 * This can also be replaced by a mock and is usually a matter of preference (mocks vs fakes in tests)
 */
class OlxApiServiceTestFake:OlxApiService {
    lateinit var input: String
    private val json = Json { ignoreUnknownKeys=true }

    override suspend fun getCarList(): CarsResponse {
        return json.decodeFromString(input)
    }
}