package by.yablonski.templateproject.networking.request

import by.yablonski.templateproject.networking.messenger.MessageRequest
import by.yablonski.templateproject.networking.messenger.ResponseDataException
import by.yablonski.templateproject.networking.proxy.Proxy
import by.yablonski.templateproject.networking.proxy.ProxyUrl
import org.json.JSONException
import org.json.JSONObject

/**
 * Sample GET request
 */
class NextNumber {

    class Request : MessageRequest<JSONObject, Result>() {

        override fun getUrlMethod(): ProxyUrl {
            return ProxyUrl("todo", "nextNumber")
        }

        override fun getMethod(): Proxy.Method = Proxy.Method.GET

        override fun parseResponse(data: JSONObject): Result {
            try {
                return Result(
                    newValue = data.getInt("data")
                )
            } catch (exception: JSONException) {
                throw ResponseDataException(exception)
            }
        }
    }

    /**
     * DTO of API response
     */
    data class Result(
        val newValue: Int
    )
}
