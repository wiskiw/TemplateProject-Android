package by.yablonski.templateproject.networking.messenger

import by.yablonski.templateproject.networking.messenger.indicator.ErrorIndicator
import by.yablonski.templateproject.networking.messenger.indicator.ProgressIndicator

/**
 * Describe a "person", who knows how to send a request to server
 */
interface Messenger<T : Any> {

    var progressIndicator : ProgressIndicator?
    var errorIndicator: ErrorIndicator?

    suspend fun send(): MessageResult<T>

}