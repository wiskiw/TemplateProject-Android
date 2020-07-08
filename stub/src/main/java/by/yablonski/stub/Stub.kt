package by.yablonski.stub

/**
 * Stub environment singleton class.
 * Can be used for storing or modifying some data
 */
object Stub {

    private var numberStore = 0

    /**
     * Demo function. Could be remove
     */
    fun getIncreaseNumber(): Int {
        val number = numberStore
        numberStore++
        return number
    }

}
