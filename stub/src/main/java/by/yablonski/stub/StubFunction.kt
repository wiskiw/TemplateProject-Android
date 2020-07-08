package by.yablonski.stub

import org.json.JSONObject

/**
 * Base stub function class to override HTTP methods for your needs.
 */
abstract class StubFunction {

    protected object HttpCode {
        const val OK = 200
    }

    open fun post(requestHeaders: Map<String, String>, requestBody: JSONObject): ByteArray {
        throw IllegalArgumentException("POST method is not implemented!")
    }

    open fun get(requestHeaders: Map<String, String>, requestBody: JSONObject): ByteArray {
        throw IllegalArgumentException("GET method is not implemented!")
    }

}
