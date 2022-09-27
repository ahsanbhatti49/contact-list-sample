package com.contactlist.currency.data.api.network

import retrofit2.Response
import retrofit2.Retrofit

inline fun <reified T> Retrofit.create(): T = create(T::class.java)

/**
 * Converts Retrofit [Response] to [Resource] which provides state
 * and data to the UI.
 */
fun <ResultType> Response<ResultType>.toResource(): Resource<ResultType> {
    val error = errorBody()?.toString() ?: message()
    return when {
        isSuccessful -> {
            val body = body()
            when {
                body != null -> Resource.success(body, this.code())
                else -> Resource.error(error, this.code())
            }
        }
        else -> Resource.error(error, this.code())
    }
}