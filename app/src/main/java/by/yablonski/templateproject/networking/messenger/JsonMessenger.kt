package by.yablonski.templateproject.networking.messenger

import android.content.Context
import by.yablonski.templateproject.R
import by.yablonski.templateproject.networking.messenger.indicator.ErrorIndicator
import by.yablonski.templateproject.networking.messenger.indicator.ProgressIndicator
import by.yablonski.templateproject.networking.proxy.Proxy
import by.yablonski.templateproject.networking.proxy.ProxyResult
import by.yablonski.templateproject.networking.proxy.exception.NetworkException
import by.yablonski.templateproject.networking.proxy.exception.NotOkHttpException
import by.yablonski.templateproject.networking.proxy.exception.ProxyException
import org.json.JSONObject
import org.slf4j.LoggerFactory

/**
 * Messenger implementation to send JSON [request] to a server via [proxy].
 */
open class JsonMessenger<T : Any>(
    private val context: Context,
    private val proxy: Proxy<JSONObject>,
    private val request: MessageRequest<JSONObject, T>
) : Messenger<T> {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(JsonMessenger::class.java)
    }

    override var progressIndicator: ProgressIndicator? = null
    override var errorIndicator: ErrorIndicator? = null

    override suspend fun send(): MessageResult<T> {
        progressIndicator?.show()
        errorIndicator?.hide()

        val proxyResult = proxy.send(
            request.getMethod(),
            request.getUrlMethod(),
            request.headers,
            request.params
        )

        progressIndicator?.hide()

        return handleProxyResult(proxyResult)
    }

    private fun handleProxyResult(proxyResult: ProxyResult<JSONObject>): MessageResult<T> {
        when (proxyResult) {
            is ProxyResult.Success -> {
                return handleSuccessResult(proxyResult.data)
            }
            is ProxyResult.Error -> {
                return when (proxyResult.exception) {
                    is NotOkHttpException -> handleHttpException(proxyResult.exception)
                    is NetworkException -> handleNetworkException(proxyResult.exception)
                    else -> handleOtherProxyException(proxyResult.exception)
                }
            }
        }
    }

    protected fun handleSuccessResult(data: JSONObject): MessageResult<T> {
        try {
            val resultData = request.parseResponse(data)
            return MessageResult.Success(resultData)

        } catch (exception: ResponseDataException) {
            return handleResponseDataException(exception)
        }
    }

    /**
     * Response data or data parsing exception handler
     */
    protected fun handleResponseDataException(exception: ResponseDataException): MessageResult<T> {
        LOGGER.error("JSON response parsing failed!", exception)

        val message = exception.message ?: context.getString(R.string.network_messaging_response_parse_error)
        errorIndicator?.showError(message)
        return MessageResult.Error(message, exception)
    }

    /**
     * Handler for HTTP response exceptions. See [NotOkHttpException]
     */
    protected fun handleHttpException(exception: NotOkHttpException): MessageResult<T> {
        val message = context.getString(R.string.network_messaging_http_error)
        errorIndicator?.showError(message)
        return MessageResult.Error(message, exception)
    }

    /**
     * Handler for no connection or other network problem exception. See [NetworkException]
     */
    protected fun handleNetworkException(exception: NetworkException): MessageResult<T> {
        val message = context.getString(R.string.network_messaging_network_error)
        errorIndicator?.showError(message)
        return MessageResult.Error(message, exception)
    }

    /**
     * Handler for other [ProxyException] exceptions
     */
    protected fun handleOtherProxyException(exception: Exception): MessageResult<T> {
        val message = context.getString(R.string.network_messaging_unhandled_error)
        errorIndicator?.showError(message)
        return MessageResult.Error(message, exception)
    }

}
