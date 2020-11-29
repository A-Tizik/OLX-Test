package com.olx.autos.codechallenge.features.cars.repositories

import com.olx.autos.codechallenge.network.api.OlxApiService
import com.olx.autos.codechallenge.network.models.CarsResponse
import okhttp3.HttpUrl
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing [Car] related endpoints
 * All requests are encapsulated in [Result] so we don't have to try/catch in upper layers of the app
 * This can also be replace with a Flow/RxJava stream which provides first a local version, and then updated from the network version
 */
@Singleton
class CarsRepository @Inject constructor(private val network: OlxApiService) {
    //Local cache to skip updating the list when it's already available and update is not necessary
    //In production app this would ideally be saved to disk/db, and disk/db would be injected here alongside network dependency
    private var carsCache: List<Car>? = null

    suspend fun getCar(id: String, update: Boolean = true):Result<Car> = runCatching {
        if(!update) {
            carsCache?.let { return@runCatching it }
        }
        carMapper(network.getCarList()).also { carsCache = it }
    }.map { list -> list.first { it.id==id } }

    suspend fun getCars(update:Boolean = true):Result<List<Car>> = runCatching {
        if(!update) {
            carsCache?.let { return@runCatching it }
        }
        carMapper(network.getCarList()).also { carsCache = it }
    }
}

/**
 * Domain-level [Car] object which is mapped from [CarsResponse.Car]
 * This is used as one of Clean Coding principles to separate models between layers,
 * that way change in for example network response doesn't affect the other layers.
 */
data class Car(
    val id: String,
    val image: HttpUrl,
    val make: Make,
    val mileage: Int,
    val name: String,
    val price: Int,
    val year: Int
) {
    data class Make(
        val key: String,
        val label: String
    )
}

private fun carMapper(carsResponse: CarsResponse): List<Car> =
    carsResponse.data.map {
        val make = Car.Make(
            key = it.make.key,
            label = it.make.label
        )

        Car(
            id = it.id,
            image = HttpUrl.get(it.image_url),
            make = make,
            mileage = it.mileage,
            name = it.name,
            price = it.price,
            year = it.year
        )
    }
