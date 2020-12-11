package by.yablonski.stub.function

import by.yablonski.stub.StubFunctionResult
import by.yablonski.stub.environment.MainStubEnvironment
import org.json.JSONObject

/**
 * Example for stub function implementation
 */
internal class NextNumber(
    private val mainStubEnvironment: MainStubEnvironment
) : StubFunction {

    override fun invoke(headers: Map<String, String>, body: JSONObject): StubFunctionResult {
        val responseBody = JSONObject()
            .apply {
                put("data", mainStubEnvironment.getIncreaseNumber())
            }

        return StubFunctionResult(body = responseBody)
    }

}
