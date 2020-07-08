package com.solabe.paydaybank.utils

sealed class RequestResult<out T : Any> {

    inline fun onSuccess(function: (T) -> Unit): RequestResult<T> {
        when (this) {
            is RequestSuccess -> function.invoke(this.data)
        }
        return this
    }

    inline fun onFailure(function: () -> Unit): RequestResult<T> {
        when (this) {
            is RequestError -> function.invoke()
        }
        return this
    }

}

data class RequestSuccess<out T : Any>(val data: T) : RequestResult<T>()
data class RequestError(val status: Int = 0, val tw: Throwable?) : RequestResult<Nothing>()

