package com.olx.autos.codechallenge.network.models

import kotlinx.serialization.Serializable

/**
 * Network response model for:
 * https://storage.googleapis.com/code-challenge/cars.json
 */
@Serializable
data class CarsResponse(
    val data: List<Car>
) {
    @Serializable
    data class Car(
        val id: String,
        val image_url: String,
        val make: Make,
        val mileage: Int,
        val name: String,
        val price: Int,
        val year: Int
    ) {
        @Serializable
        data class Make(
            val key: String,
            val label: String
        )
    }

}

