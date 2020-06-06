package by.yablonski.templateproject.networking.proxy.exception

/**
 * Exception for HTTP response, where HTTP status code is not 200
 */
class NotOkHttpException : ProxyException {

    val code: Int
    val data: ByteArray

    constructor(code: Int, data: ByteArray, message: String) : super(message) {
        this.code = code
        this.data = data
    }

    constructor(code: Int, data: ByteArray, cause: Throwable) : super(cause) {
        this.code = code
        this.data = data
    }

    constructor(code: Int, data: ByteArray, message: String, cause: Throwable) : super(message, cause) {
        this.code = code
        this.data = data
    }


}
