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
class NextNumberRequest : MessageRequest<JSONObject, Int>() {

    override fun getUrlMethod(): ProxyUrl {
        return ProxyUrl("todo", "nextNumber")
    }

    override fun getMethod(): Proxy.Method = Proxy.Method.GET

    override fun parseResponse(data: JSONObject): Int {
        try {
            return data.getInt("data")
        } catch (exception: JSONException) {
            throw ResponseDataException(exception)
        }
    }
}