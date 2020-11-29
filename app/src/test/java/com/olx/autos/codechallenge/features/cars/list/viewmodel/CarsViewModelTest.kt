package com.olx.autos.codechallenge.features.cars.list.viewmodel

import app.cash.turbine.test
import com.olx.autos.codechallenge.OlxApiServiceTestFake
import com.olx.autos.codechallenge.Resource
import com.olx.autos.codechallenge.features.cars.repositories.Car
import com.olx.autos.codechallenge.features.cars.repositories.CarsRepository
import com.olx.autos.codechallenge.features.cars.repositories.carsInputMalformed
import com.olx.autos.codechallenge.features.cars.repositories.carsInputThreeCars
import com.olx.autos.codechallenge.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CarsViewModelTest {

    private lateinit var carsRepository: CarsRepository
    private lateinit var networkFake: OlxApiServiceTestFake
    private lateinit var dispatcher: TestCoroutineDispatcher
    @Before
    fun setup() {
        networkFake = OlxApiServiceTestFake()
        carsRepository = CarsRepository(networkFake)
        dispatcher = TestCoroutineDispatcher()
    }


    @Test
    fun carsFlowContainsCars() {
        runBlockingTest {
            //Given
            networkFake.input = carsInputThreeCars

            //When
            dispatcher.pauseDispatcher()
            val viewModel = CarsViewModel(carsRepository,dispatcher)

            //Then
            val data = viewModel.filteredCarsFlow.test {
                dispatcher.resumeDispatcher()
                assertTrue(expectItem() is Resource.Loading)
                val item:Resource<List<Car>> = expectItem()
                assertTrue(item is Resource.Success)
                item.map {
                    assertEquals(3,it.count())
                }
            }
        }
    }


    @Test
    fun carsFlowContainsErrorOnMalformedInput() {
        runBlockingTest {
            //Given
            networkFake.input = carsInputMalformed

            //When
            dispatcher.pauseDispatcher()
            val viewModel = CarsViewModel(carsRepository,dispatcher)

            //Then
            val data = viewModel.filteredCarsFlow.test {
                dispatcher.resumeDispatcher()
                assertTrue(expectItem() is Resource.Loading)
                assertTrue(expectItem() is Resource.Error)
            }
        }
    }

    @Test
    fun carsFlowIsFilteredByName() {
        runBlocking {
            //Given
            networkFake.input = carsInputThreeCars

            //When
            dispatcher.pauseDispatcher()
            val viewModel = CarsViewModel(carsRepository,dispatcher)
            viewModel.onCarNameChangedFilter("Cayenne")

            //Then
            val data = viewModel.filteredCarsFlow.test {
                dispatcher.resumeDispatcher()
                assertTrue(expectItem() is Resource.Loading)
                val item = expectItem()
                assertTrue(item is Resource.Success)
                item.map {
                    assertEquals(1,it.count())
                    assertEquals("Cayenne",it.first().name)
                }
            }
        }
    }

}