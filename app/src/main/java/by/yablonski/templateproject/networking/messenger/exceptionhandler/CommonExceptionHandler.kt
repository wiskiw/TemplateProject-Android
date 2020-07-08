package by.yablonski.templateproject.networking.messenger.exceptionhandler

import android.content.Context
import by.yablonski.templateproject.R
import by.yablonski.templateproject.networking.messenger.MessageResult
import by.yablonski.templateproject.networking.messenger.ResponseDataException
import by.yablonski.templateproject.networking.proxy.exception.NetworkException
import by.yablonski.templateproject.networking.proxy.exception.NotOkHttpException
import by.yablonski.templateproject.networking.proxy.exception.ProxyException
import by.yablonski.templateproject.networking.proxy.exception.StubException
import by.yablonski.templateproject.networking.proxy.volley.VolleyJsonProxy
import org.slf4j.LoggerFactory

class CommonExceptionHandler<T : Any>(private val context: Context) : ExceptionHandler<T> {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CommonExceptionHandler::class.java)
    }

    /**
     * Response data or data parsing exception handler
     */
    override fun handleResponseDataException(exception: ResponseDataException): MessageResult<T> {
        LOGGER.error("On response data exception!", exception)
        val message = exception.message ?: context.getString(R.string.network_messaging_response_parse_error)
        return MessageResult.Error(message, exception)
    }

    override fun handleProxyException(exception: ProxyException): MessageResult<T> {
        LOGGER.error("On proxy exception!", exception)
        return when (exception) {
            is NotOkHttpException -> handleHttpException(exception)
            is NetworkException -> handleNetworkException(exception)
            else -> handleOtherProxyException(exception)
        }
    }

    /**
     * Handler for HTTP response exceptions. See [NotOkHttpException]
     */
    private fun handleHttpException(exception: NotOkHttpException): MessageResult<T> {
        val message = context.getString(R.string.network_messaging_http_error)
        return MessageResult.Error(message, exception)
    }

    /**
     * Handler for no connection or other network problem exception. See [NetworkException]
     */
    private fun handleNetworkException(exception: NetworkException): MessageResult<T> {
        val message = context.getString(R.string.network_messaging_network_error)
        return MessageResult.Error(message, exception)
    }

    /**
     * Handler for other [ProxyException] exceptions
     */
    private fun handleOtherProxyException(exception: Exception): MessageResult<T> {
        val message = context.getString(R.string.network_messaging_unhandled_error)
        return MessageResult.Error(message, exception)
    }

}
