package com.olx.autos.codechallenge

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


/**
 * A generic class that holds a value with its loading status.
 * Convenient wrapper for observing data from UI layer to display
 */
sealed class Resource<out T> {
    abstract val data: T?

    class Success<out T>(override val data: T) : Resource<T>()

    class Loading<out T>(override val data: T? = null) : Resource<T>()

    class Error<out T>(override val data: T?, val message: String) : Resource<T>()

}

/**
 * Returns the encapsulated result of the given [transform] function applied to the encapsulated value
 * if this instance contains the data, and retains the error message if the instance is an Error
 */
@OptIn(ExperimentalContracts::class)
fun <T,S> Resource<T>.map(transform: (T) -> S):Resource<S> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when(this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Loading -> Resource.Loading(data?.let(transform))
        is Resource.Error -> Resource.Error(data?.let(transform),message)
    }
}

/**
 * Convenience function for shorter declaration of MutableStateFlow with Resource
 */
fun <T> ResourceFlow(resource: Resource<T> = Resource.Loading<T>()) =
    MutableStateFlow(resource)

/**
 * Convenience function for converting from Result to a Resource flow, with default error message
 */
fun <T> Result<T>.toResourceFlow(
    mutableStateFlow: MutableStateFlow<Resource<T>>,
    errorMessage: (Throwable) -> String = { "An error has occurred. Please try again later" }
) {
    mutableStateFlow.value =
        if (isSuccess) {
            Resource.Success(getOrThrow())
        } else {
            Resource.Error(mutableStateFlow.value.data, errorMessage(exceptionOrNull()!!))
        }
}
