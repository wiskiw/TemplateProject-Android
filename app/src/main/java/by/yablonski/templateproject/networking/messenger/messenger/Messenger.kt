package by.yablonski.templateproject.networking.messenger.messenger

import by.yablonski.templateproject.networking.messenger.MessageResult
import by.yablonski.templateproject.networking.messenger.exceptionhandler.ExceptionHandler
import by.yablonski.templateproject.networking.messenger.indicator.ErrorIndicator
import by.yablonski.templateproject.networking.messenger.indicator.ProgressIndicator

/**
 * Describe a "person", who knows how to send a request to server
 */
interface Messenger<T : Any> {

    var progressIndicator : ProgressIndicator?
    var errorIndicator: ErrorIndicator?
    var exceptionHandler: ExceptionHandler<T>

    suspend fun send(): MessageResult<T>

}