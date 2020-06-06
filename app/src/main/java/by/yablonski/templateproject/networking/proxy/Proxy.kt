package by.yablonski.templateproject.networking.proxy

import by.yablonski.templateproject.BuildConfig
import org.json.JSONObject

/**
 * Describes how to interact with the server.
 *
 * Should check [BuildConfig.ENABLE_PROXY_LOGGING] for enabling logging.
 */
interface Proxy<T : Any> {

    enum class Method {
        GET,
        POST,
    }

    suspend fun send(
        method: Method,
        proxyUrl: ProxyUrl,
        headers: Map<String, String>,
        body: JSONObject
    ): ProxyResult<T>

}
