package by.yablonski.stub.function

import by.yablonski.stub.Stub
import by.yablonski.stub.StubFunction
import by.yablonski.stub.Utils
import org.json.JSONObject

/**
 * Example for stub function implementation
 */
class NextNumber : StubFunction() {

    override fun get(requestHeaders: Map<String, String>, requestBody: JSONObject): ByteArray {
        val body = JSONObject()
        body.put("data", Stub.getIncreaseNumber())

        return Utils.createJsonStubResponse(
            code = HttpCode.OK,
            body = body
        )
    }

}
