package by.yablonski.templateproject.networking.messenger

import by.yablonski.templateproject.networking.proxy.Proxy
import by.yablonski.templateproject.networking.proxy.ProxyUrl
import org.json.JSONObject

/**
 * A message to server. Contains information to connect to a [Proxy] server (url, headers, params, etc.).
 * And how to handle a response from [Proxy].
 */
abstract class MessageRequest<in I : Any, out O : Any> {

    open val headers: Map<String, String> = mapOf()

    open val params: JSONObject = JSONObject()

    abstract fun getUrlMethod(): ProxyUrl

    abstract fun getMethod(): Proxy.Method

    @Throws(ResponseDataException::class)
    abstract fun parseResponse(data: I): O

}