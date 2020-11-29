package com.olx.autos.codechallenge.features.cars.detailed.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.olx.autos.codechallenge.ResourceFlow
import com.olx.autos.codechallenge.features.cars.repositories.Car
import com.olx.autos.codechallenge.features.cars.repositories.CarsRepository
import com.olx.autos.codechallenge.toResourceFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * In addition to [CarsRepository] Dagger provides an assisted injection of [SavedStateHandle]
 * It is used to get a bundle arg from the previous screen, that way the [DetailedCarFragment]
 * doesn't have to pass the [carId] manually
 *
 * In case there is any transient data that shouldn't be stored in database, but still
 * should survive process death, it can also be saved in a [SavedStateHandle]
 */
class DetailedCarViewModel @ViewModelInject constructor(
    private val carsRepository: CarsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(),
    CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.Default) {

    private val carId = savedStateHandle.get<String>(ARGUMENT_TAG) ?: throw IllegalArgumentException("Car ID is required")

    val carFlow = ResourceFlow<Car>()

    init {
        onCarRefresh(false)
    }

    fun onCarRefresh(update:Boolean=true) {
        launch {
            carsRepository.getCar(carId,update = update).toResourceFlow(carFlow)
        }
    }
    companion object {
        const val ARGUMENT_TAG = "car_id"
    }
}
