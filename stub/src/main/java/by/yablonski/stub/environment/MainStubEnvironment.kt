package by.yablonski.stub.environment

/**
 * Stub environment singleton class.
 * Can be used for storing or modifying some data
 */
class MainStubEnvironment {

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