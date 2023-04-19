package com.andriidubovyk.wordsnap.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : com.andriidubovyk.wordsnap.common.Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : com.andriidubovyk.wordsnap.common.Resource<T>(data, message)
    class Loading<T>(data: T? = null) : com.andriidubovyk.wordsnap.common.Resource<T>(data)
}
