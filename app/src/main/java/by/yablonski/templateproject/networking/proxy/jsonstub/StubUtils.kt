package by.yablonski.templateproject.networking.proxy.jsonstub

import org.json.JSONObject
import org.slf4j.LoggerFactory
import java.util.*

object StubUtils {

    private val LOGGER = LoggerFactory.getLogger(StubUtils::class.java)

    private const val STUB_FUNCTIONS_PACKAGE_PATH = "by.yablonski.stub.function"

    private val SNAKE_REGEX = "_[a-zA-Z]".toRegex()
    private val KEBAB_REGEX = "-[a-zA-Z]".toRegex()

    @Throws(ClassNotFoundException::class)
    fun invokeStubFunctionMethod(
        method: String,
        functionName: String,
        headers: Map<String, String>,
        body: JSONObject
    ): ByteArray {

        val stubClassPath = buildFunctionClassPath(functionName)

        val stubFunctionObject = Class.forName(stubClassPath).newInstance()
        val stubFunctionMethod = stubFunctionObject.javaClass.getDeclaredMethod(
            method,
            Map::class.java,
            JSONObject::class.java
        )

        val stubFunctionMethodResult = stubFunctionMethod.invoke(stubFunctionObject, headers, body)
        return stubFunctionMethodResult as ByteArray
    }

    /**
     * Create a path to stub function class
     */
    private fun buildFunctionClassPath(functionName: String): String {
        val stubClassName = convertFunctionNameToStubClassName(functionName)
        return "$STUB_FUNCTIONS_PACKAGE_PATH.$stubClassName"
    }

    /**
     * Convert function name to actual stub class name
     *
     * Example:
     *      foo-name -> FooName
     *      fooName -> FooName
     *      Foo_Name -> FooName
     */
    private fun convertFunctionNameToStubClassName(functionName: String): String {
        val snake = SNAKE_REGEX.replace(functionName) {
            it.value.replace("_", "").toUpperCase(Locale.ROOT)
        }
        val snakeKebab = KEBAB_REGEX.replace(snake) {
            it.value.replace("-", "").toUpperCase(Locale.ROOT)
        }
        return snakeKebab.capitalize()

    }


}
