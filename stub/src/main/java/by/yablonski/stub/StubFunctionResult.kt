package by.yablonski.stub

import org.json.JSONObject


data class StubFunctionResult(
    val delay: Long = DEFAULT_DELAY,
    val code: Int = HttpCode.OK,
    val body: JSONObject
) {

    companion object {
        private const val DEFAULT_DELAY = 600L
    }

    object HttpCode {
        const val OK = 200
    }

}
