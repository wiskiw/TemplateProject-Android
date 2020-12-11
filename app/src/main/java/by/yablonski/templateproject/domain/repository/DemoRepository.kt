package by.yablonski.templateproject.domain.repository

import by.yablonski.templateproject.app.App
import by.yablonski.templateproject.networking.messenger.MessageResult
import by.yablonski.templateproject.networking.messenger.messenger.JsonMessenger
import by.yablonski.templateproject.networking.request.NextNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Repository to interact with data in local DB, via REST API request etc.
 * The main responsibilities:
 *      - convert objects from DTO to client(app) models
 *      - merge data from different sources (ex: local DB and API)
 *      - encapsulate cache logic
 */
class DemoRepository(
    private val app: App
) {

    fun increaseNumber(
        onSuccess: (newValue: Int) -> Unit?,
        onFailed: (message: String) -> Unit?
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = JsonMessenger(app, app.getJsonProxy(), NextNumber.Request())
                .send()

            when (result) {
                is MessageResult.Success -> onSuccess.invoke(result.data.newValue)
                is MessageResult.Error -> onFailed.invoke(result.message)
            }

        }
    }
}
