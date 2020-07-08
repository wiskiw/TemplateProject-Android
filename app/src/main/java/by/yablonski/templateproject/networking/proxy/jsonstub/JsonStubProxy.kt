package by.yablonski.templateproject.networking.proxy.jsonstub

import by.yablonski.templateproject.networking.proxy.Proxy
import by.yablonski.templateproject.networking.proxy.ProxyResult
import by.yablonski.templateproject.networking.proxy.ProxyUrl
import by.yablonski.templateproject.networking.proxy.exception.NotOkHttpException
import by.yablonski.templateproject.networking.proxy.exception.StubException
import kotlinx.coroutines.delay
import org.json.JSONException
import org.json.JSONObject
import org.slf4j.LoggerFactory
import java.util.*


class JsonStubProxy : Proxy<JSONObject> {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(JsonStubProxy::class.java)
        private const val HTTP_CODE_OK = 200
    }

    private object StubResultFields {
        const val DELAY = "delay"
        const val CODE = "code"
        const val BODY = "body"
    }

    override suspend fun send(
        method: Proxy.Method,
        proxyUrl: ProxyUrl,
        headers: Map<String, String>,
        body: JSONObject
    ): ProxyResult<JSONObject> {

        try {
            val stubResultBytes = StubUtils.invokeStubFunctionMethod(
                proxyMethodToString(method),
                proxyUrl.functionName,
                headers,
                body
            )
            return createProxyResult(stubResultBytes)

        } catch (exception: ClassNotFoundException) {
            val stubException = StubException(
                "Stub function for '${proxyUrl.functionName}' not found!",
                exception
            )
            return ProxyResult.Error(stubException)
        } catch (exception: NoSuchMethodException) {
            val stubException = StubException(
                "Stub function for '${proxyUrl.functionName}' doesn't implement '${method}' method!",
                exception
            )
            return ProxyResult.Error(stubException)
        }

    }

    private fun proxyMethodToString(method: Proxy.Method): String = method.name.toLowerCase(Locale.getDefault())


    private suspend fun createProxyResult(stubResultBytes: ByteArray): ProxyResult<JSONObject> {
        val jsonStubResult = stubResultToJson(stubResultBytes)

        try {
            val stubResult = convertJsonResultToStubResult(jsonStubResult)
            delay(stubResult.delay)

            if (stubResult.code == HTTP_CODE_OK) {
                return ProxyResult.Success(stubResult.body)

            } else {
                val httpException = NotOkHttpException(
                    stubResult.code,
                    stubResult.body.toString().toByteArray(Charsets.UTF_8),
                    StubException(stubResultBytes)
                )
                return ProxyResult.Error(httpException)
            }

        } catch (jsonException: JSONException) {
            val stubException = StubException(
                stubResultBytes,
                jsonException
            )
            return ProxyResult.Error(stubException)
        }
    }

    private fun stubResultToJson(stubResult: ByteArray): JSONObject {
        val stubString = stubResult.toString(Charsets.UTF_8)
        return JSONObject(stubString)
    }


    @Throws(JSONException::class)
    private fun convertJsonResultToStubResult(jsonStubResult: JSONObject): StubResult {
        val delay = jsonStubResult.getLong(StubResultFields.DELAY)
        val code = jsonStubResult.getInt(StubResultFields.CODE)
        val body = jsonStubResult.getJSONObject(StubResultFields.BODY)

        return StubResult(
            delay,
            code,
            body
        )
    }

    private data class StubResult(
        val delay: Long,
        val code: Int,
        val body: JSONObject
    )

}
