package by.yablonski.templateproject.app

import java.lang.StringBuilder

/**
 * Класс с общими утилитами
 *
 * @author Andrey Yablonsky
 */
object Utils {

    @JvmStatic
    fun isBlankOrNull(value: String?): Boolean {
        return value.isNullOrBlank()
    }

    /**
     * Возвращает значение из [range], которое наиболее близкое к [value].
     * @return [value] или наиболее близкое к нему из [range]
     */
    @JvmStatic
    fun <T : Comparable<T>> getInRange(range: ClosedFloatingPointRange<T>, value: T): T {
        return value.coerceIn(range)
    }

    @JvmStatic
    fun checkTrue(expression: Boolean, messageFormat: String, vararg arguments: Any) {
        if (!expression) {
            throw IllegalArgumentException(String.format(messageFormat, *arguments))
        }
    }

    /**
     * Asserts that specified expression is false.
     *
     * @param expression boolean expression
     * @param messageFormat String representing message format
     * @param arguments vararg array of message format parameters.
     * @throws IllegalArgumentException if object is false.
     */
    @JvmStatic
    fun checkFalse(expression: Boolean, messageFormat: String, vararg arguments: Any) {
        checkTrue(!expression, messageFormat, *arguments)
    }

    @JvmStatic
    fun checkNotNull(obj: Any?, messageFormat: String, vararg arguments: Any) {
        // todo добавить kotlin.contract
//        contract {
//            returns() implies (value != null)
//        }
        checkTrue(obj != null, messageFormat, arguments)
    }

    @JvmStatic
    fun <T> isContains(vararg source: T, item: T): Boolean {
        return source.contains(item)
    }

    @JvmStatic
    fun trimTo16Bit(int: Int): Int {
        val mask16Bit = 0xffff // 00000000 00000000 11111111 11111111
        return int and mask16Bit
    }

    @JvmStatic
    fun joinToString(iterable: Iterable<*>, separator: String): String {
        return iterable.joinToString(separator = separator)
    }


    /**
     * Создает URL адрес запроса из частей #parts
     *
     * Пример:
     *
     * Входные данные: https://api.domdara.com , /v2/// , /currency/\rate
     * Результат: https://api.domdara.com/v2/currency/rate
     */
    fun buildUrl(endpoint: String, vararg parts: String): String {
        val slash = "/"
        val empty = ""
        val fullUrl = StringBuilder(endpoint)

        // detects slashes at start and end of string
        val sidesSlashesRegex = String.format("\\%s+$|^\\%s+", slash, slash)
        for (part in parts) {
            val clearParam: String = part
                .replace("\\", slash)
                .replace("\\s", empty)
                .replace(sidesSlashesRegex.toRegex(), empty) // trim slashes

            fullUrl
                .append(clearParam)
                .append(slash)
        }
        return fullUrl
            .toString()
            .replace(sidesSlashesRegex.toRegex(), empty)
    }
}