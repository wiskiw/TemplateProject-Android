package by.yablonski.templateproject.networking.messenger.exceptionhandler

import by.yablonski.templateproject.networking.messenger.MessageResult
import by.yablonski.templateproject.networking.messenger.ResponseDataException
import by.yablonski.templateproject.networking.proxy.exception.ProxyException

/**
 * Handle [Messenger] exceptions
 */
interface ExceptionHandler<T : Any> {

    /**
     * Handling response data or data parsing exceptions. See [ResponseDataException]
     */
    fun handleResponseDataException(exception: ResponseDataException): MessageResult<T>

    /**
     * Handling any [ProxyException] exceptions
     */
    fun handleProxyException(exception: ProxyException): MessageResult<T>

}
