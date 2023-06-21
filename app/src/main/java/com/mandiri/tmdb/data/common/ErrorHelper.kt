package com.mandiri.tmdb.data.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.io.IOException

object ErrorHelper {
    fun <T> resolve(response: Response<T>): Throwable {
        val type = object : TypeToken<ErrorResponse>(){}.type
        val error = Gson().fromJson<ErrorResponse>(response.errorBody()!!.charStream(), type)
        return IOException(error.statusMessage)
    }
}