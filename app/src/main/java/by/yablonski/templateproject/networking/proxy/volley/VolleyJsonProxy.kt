package by.yablonski.templateproject.networking.proxy.volley

import android.content.Context
import by.yablonski.templateproject.BuildConfig
import by.yablonski.templateproject.networking.proxy.Proxy
import by.yablonski.templateproject.networking.proxy.ProxyResult
import by.yablonski.templateproject.networking.proxy.ProxyUrl
import by.yablonski.templateproject.networking.proxy.exception.NetworkException
import by.yablonski.templateproject.networking.proxy.exception.NotOkHttpException
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.slf4j.LoggerFactory
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * [Proxy] implementation that use [Volley] to make HTTP calls for JSON objects.
 */
class VolleyJsonProxy(context: Context) : Proxy<JSONObject> {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(VolleyJsonProxy::class.java)
        private val VOLLEY_METHOD_MAPPING = mapOf(
            Pair(Proxy.Method.GET, Request.Method.GET),
            Pair(Proxy.Method.POST, Request.Method.POST)
        )
    }

    private val queue = Volley.newRequestQueue(context)

    override suspend fun send(
        method: Proxy.Method,
        proxyUrl: ProxyUrl,
        headers: Map<String, String>,
        body: JSONObject
    ): ProxyResult<JSONObject> {

        return suspendCoroutine { cont ->
            val request = MutableJsonRequest(
                convertToVolleyMethod(method),
                proxyUrl.getUrl(),
                body,
                Response.Listener { cont.resume(onSuccess(it)) },
                Response.ErrorListener { cont.resume(onError(it)) }
            )
            request.headers = headers

            if (BuildConfig.ENABLE_PROXY_LOGGING) {
                LOGGER.debug(
                    "Sending Volley [$method] request to '${proxyUrl.getUrl()}'\n" +
                            "headers: '$headers'\n" +
                            "body: '$body'"
                )
            }

            queue.add(request)
        }
    }

    private fun onSuccess(response: JSONObject): ProxyResult.Success<JSONObject> {
        if (BuildConfig.ENABLE_PROXY_LOGGING) {
            LOGGER.debug("Volley request success: '$response'")
        }

        return ProxyResult.Success(response)
    }

    private fun onError(volleyError: VolleyError): ProxyResult.Error<JSONObject> {

        if (VolleyUtils.isNetworkProblem(volleyError)) {
            if (BuildConfig.ENABLE_PROXY_LOGGING) {
                LOGGER.error("Volley request failed - network exception!", volleyError)
            }

            val networkException = NetworkException(volleyError)
            return ProxyResult.Error(networkException)

        } else {
            val code = volleyError.networkResponse.statusCode
            val responseData = volleyError.networkResponse.data

            if (BuildConfig.ENABLE_PROXY_LOGGING) {
                LOGGER.error(
                    "Volley request failed [code=${code}]! Data: '${responseData.toString(Charsets.UTF_8)}'",
                    volleyError
                )
            }

            val httpException = NotOkHttpException(code, responseData, volleyError)
            return ProxyResult.Error(httpException)
        }
    }

    private fun convertToVolleyMethod(method: Proxy.Method): Int = VOLLEY_METHOD_MAPPING[method] ?: throw IllegalStateException()
}
