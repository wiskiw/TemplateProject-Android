package by.yablonski.templateproject.networking.proxy

import by.yablonski.templateproject.networking.proxy.exception.ProxyException

sealed class ProxyResult<T: Any> {

    data class Success<T : Any>(val data: T) : ProxyResult<T>()
    data class Error<T : Any>(val exception: ProxyException) : ProxyResult<T>()

    override fun toString(): String {
        return when (this) {
            is Success<T> -> "Success[data=$data]"
            is Error<T> -> "Error[exception=$exception]"
        }
    }
}
