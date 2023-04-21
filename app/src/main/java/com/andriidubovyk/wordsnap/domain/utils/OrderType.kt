package com.andriidubovyk.wordsnap.domain.utils

sealed interface OrderType {
    object Ascending: OrderType
    object Descending: OrderType
}
