package by.yablonski.stub.function

import by.yablonski.stub.StubFunctionResult
import org.json.JSONObject

interface StubFunction {

    fun invoke(headers: Map<String, String>, body: JSONObject): StubFunctionResult

}
