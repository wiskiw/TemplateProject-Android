package by.yablonski.templateproject.networking.messenger

sealed class MessageResult<out T: Any> {

    data class Success<T : Any>(val data: T) : MessageResult<T>()
    data class Error(val message: String, val exception: Exception) : MessageResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<T> -> "Success[data=$data]"
            is Error -> "Error[message='$message', exception=$exception]"
        }
    }
}
