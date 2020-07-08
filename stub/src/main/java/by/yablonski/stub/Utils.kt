package by.yablonski.stub

import org.json.JSONObject

object Utils {

    private const val DEFAULT_DELAY = 600L

    fun createJsonStubResponse(delay: Long = DEFAULT_DELAY, code: Int, body: JSONObject): ByteArray {
        val response = JSONObject()
        response.put("delay", delay)
        response.put("code", code)
        response.put("body", body)

        return response.toString().toByteArray(Charsets.UTF_8)
    }

}
