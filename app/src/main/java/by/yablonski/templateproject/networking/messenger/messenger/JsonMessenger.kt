package by.yablonski.templateproject.networking.messenger.messenger

import android.content.Context
import by.yablonski.templateproject.networking.messenger.MessageRequest
import by.yablonski.templateproject.networking.messenger.MessageResult
import by.yablonski.templateproject.networking.messenger.ResponseDataException
import by.yablonski.templateproject.networking.messenger.exceptionhandler.CommonExceptionHandler
import by.yablonski.templateproject.networking.messenger.exceptionhandler.ExceptionHandler
import by.yablonski.templateproject.networking.messenger.indicator.ErrorIndicator
import by.yablonski.templateproject.networking.messenger.indicator.ProgressIndicator
import by.yablonski.templateproject.networking.proxy.Proxy
import by.yablonski.templateproject.networking.proxy.ProxyResult
import org.json.JSONObject
import org.slf4j.LoggerFactory

/**
 * Messenger implementation to send JSON [request] to a server via [proxy].
 */
open class JsonMessenger<T : Any>(
    context: Context,
    private val proxy: Proxy<JSONObject>,
    private val request: MessageRequest<JSONObject, T>
) : Messenger<T> {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(JsonMessenger::class.java)
    }

    override var progressIndicator: ProgressIndicator? = null
    override var errorIndicator: ErrorIndicator? = null
    override var exceptionHandler: ExceptionHandler<T> = CommonExceptionHandler(context)

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
        return when (proxyResult) {
            is ProxyResult.Success -> handleSuccessResult(proxyResult.data)
            is ProxyResult.Error -> exceptionHandler.handleProxyException(proxyResult.exception)
        }
    }

    private fun handleSuccessResult(data: JSONObject): MessageResult<T> {
        try {
            val resultData = request.parseResponse(data)
            return MessageResult.Success(resultData)

        } catch (exception: ResponseDataException) {
            return exceptionHandler.handleResponseDataException(exception)
        }
    }

}
