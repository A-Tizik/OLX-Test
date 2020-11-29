package com.olx.autos.codechallenge.features.cars.list.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.olx.autos.codechallenge.*
import com.olx.autos.codechallenge.features.cars.repositories.Car
import com.olx.autos.codechallenge.features.cars.repositories.CarsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private data class CarFilter(
    val name: String = "",
    val make: String = ""
)

/**
 * Simple ViewModel for managing the initial Car list screen state.
 * The dependencies are provided from Dagger graph, most of the work
 * is sent to background by coroutines working on Dispatchers.Default
 */
class CarsViewModel @ViewModelInject constructor(private val carsRepository: CarsRepository, private val dispatcher: CoroutineDispatcher = Dispatchers.Default) : ViewModel(),
    CoroutineScope by CoroutineScope(dispatcher + SupervisorJob()) {

    private val carsFlow = ResourceFlow<List<Car>>()
    private val filterFlow = MutableStateFlow<CarFilter>(CarFilter())

    val filteredCarsFlow: Flow<Resource<List<Car>>> = carsFlow.combine(filterFlow) { carsResource: Resource<List<Car>>, filter: CarFilter ->
        carsResource.map { list->
            list.filter {
                (filter.make.isBlank() || it.make.label.contains(filter.make)) &&
                        (filter.name.isBlank() || it.name.contains(filter.name))
            }
        }
    }

    init {
        onCarsRefresh()
    }

    fun onCarMakeChangedFilter(make: String) {
        filterFlow.value = filterFlow.value.copy(make = make)
    }

    fun onCarNameChangedFilter(name: String) {
        filterFlow.value = filterFlow.value.copy(name = name)
    }

    fun onCarsRefresh() {
        launch {
            carsRepository.getCars().toResourceFlow(carsFlow)
        }
    }

}