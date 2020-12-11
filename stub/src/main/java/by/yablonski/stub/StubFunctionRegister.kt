package by.yablonski.stub

import android.content.Context
import by.yablonski.stub.function.NextNumber
import by.yablonski.stub.function.StubFunction
import by.yablonski.stub.utils.StubResponseReader

class StubFunctionRegister(context: Context) {

    private object Method {
        const val GET = "get"
        const val POST = "post"
    }

    private val environmentRegister = StubEnvironmentRegister()
    private val stringAssetReader = StubResponseReader(context)

    private val methodGetFunctionMapping = mapOf(
        "nextNumber" to NextNumber(environmentRegister.commonStubEnvironment)
    )

    private val methodPostFunctionMapping = mapOf<String, StubFunction>(
//        "demoTested" to DemoTested(),
    )

    fun getStubFunction(method: String, functionName: String): StubFunction {
        val functionMapping = getFunctionMapping(method)

        return functionMapping[functionName]
            ?: throw IllegalArgumentException("Stub function for '$functionName'($method) function name not found!")
    }

    private fun getFunctionMapping(method: String): Map<String, StubFunction> {
        return when (method) {
            Method.GET -> methodGetFunctionMapping
            Method.POST -> methodPostFunctionMapping
            else -> throw IllegalArgumentException("Function mapping for '$method' method not found!")
        }
    }

}
