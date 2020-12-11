package by.yablonski.templateproject.networking.proxy.jsonstub

import android.content.Context
import by.yablonski.stub.StubFunctionRegister
import by.yablonski.stub.StubFunctionResult
import by.yablonski.templateproject.networking.proxy.Proxy
import by.yablonski.templateproject.networking.proxy.ProxyResult
import by.yablonski.templateproject.networking.proxy.ProxyUrl
import by.yablonski.templateproject.networking.proxy.exception.NotOkHttpException
import kotlinx.coroutines.delay
import org.json.JSONObject
import org.slf4j.LoggerFactory
import java.util.*


class JsonStubProxy(context: Context) : Proxy<JSONObject> {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(JsonStubProxy::class.java)

        private val HTTP_SUCCESS_CODE_RANGE = 200..299
    }

    private val stubFunctionRegister = StubFunctionRegister(context)

    override suspend fun send(
        method: Proxy.Method,
        proxyUrl: ProxyUrl,
        headers: Map<String, String>,
        body: JSONObject
    ): ProxyResult<JSONObject> {

        val methodString = method.name.toLowerCase(Locale.getDefault())
        val stubFunction = stubFunctionRegister.getStubFunction(methodString, proxyUrl.functionName)

        val stubResult = stubFunction.invoke(headers, body)
        delay(stubResult.delay)

        return parseProxyResult(stubResult)
    }

    private fun parseProxyResult(stubResult: StubFunctionResult): ProxyResult<JSONObject> {
        if (stubResult.code in HTTP_SUCCESS_CODE_RANGE) {
            return ProxyResult.Success(stubResult.body)

        } else {
            val httpException = NotOkHttpException(
                stubResult.code,
                stubResult.body.toString().toByteArray(Charsets.UTF_8),
                "Response HTTP code '${stubResult.code}' is not include it success code range '$HTTP_SUCCESS_CODE_RANGE'"
            )
            return ProxyResult.Error(httpException)
        }

    }

}
