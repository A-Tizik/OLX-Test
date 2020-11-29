package com.olx.autos.codechallenge.features.cars.repositories

import com.olx.autos.codechallenge.OlxApiServiceTestFake
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class CarsRepositoryTest {

    private lateinit var carsRepository: CarsRepository
    private lateinit var networkFake: OlxApiServiceTestFake

    @Before
    fun setup() {
        networkFake = OlxApiServiceTestFake()
        carsRepository = CarsRepository(networkFake)
    }

    @Test
    fun getCarsMalformed() {
        runBlocking {
            //Given
            networkFake.input = carsInputMalformed

            //When
            val response = carsRepository.getCars()

            //Then
            assertTrue(response.isFailure)
        }
    }

    @Test
    fun getCars() {
        runBlocking {
            //Given
            networkFake.input = carsInputThreeCars

            //When
            val response = carsRepository.getCars()

            //Then
            assertTrue(response.isSuccess)
            response.map {
                assertTrue(it.count() == 3)
                assertTrue(it[0].name == "Cayenne")
            }
        }
    }
}


