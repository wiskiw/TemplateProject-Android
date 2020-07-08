package by.yablonski.templateproject.networking.proxy.exception

/**
 * Exception for any stub issues
 */
class StubException : ProxyException {

    var data: ByteArray? = null

    constructor(data: ByteArray) : super() {
        this.data = data
    }

    constructor(data: ByteArray, message: String) : super(message) {
        this.data = data
    }

    constructor(data: ByteArray, message: String, cause: Throwable) : super(message, cause) {
        this.data = data
    }

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(data: ByteArray, cause: Throwable) : super(cause) {
        this.data = data
    }
}
