package by.yablonski.templateproject.networking.messenger.indicator

/**
 * UI component, than can show error
 */
interface ErrorIndicator {

    fun showError(message: String)

    fun hide()

    val isVisible: Boolean

}
