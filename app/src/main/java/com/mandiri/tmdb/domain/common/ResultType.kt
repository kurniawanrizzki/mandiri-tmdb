package com.mandiri.tmdb.domain.common

sealed class ResultType<out T : Any>{
    object Loading : ResultType<Nothing>()
    data class Success<out T : Any>(val data: T) : ResultType<T>()
    data class Error(val e: Throwable) : ResultType<Nothing>()
}
