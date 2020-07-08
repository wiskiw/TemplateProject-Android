package by.yablonski.templateproject.app

import android.app.Application
import by.yablonski.templateproject.app.logging.LoggerAggregator
import by.yablonski.templateproject.app.logging.SLF4JLoggerAdapter
import by.yablonski.templateproject.app.logging.logger.FileLogger
import by.yablonski.templateproject.app.logging.logger.LogcatLogger
import by.yablonski.templateproject.networking.proxy.Proxy
import by.yablonski.templateproject.networking.proxy.jsonstub.JsonStubProxy
import org.json.JSONObject
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException

class App : Application() {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(App::class.java)

        private const val LOG_FILE_MAX_SIZE_BYTE = 512 * 1024
    }

    private lateinit var logFileName: String

    @Volatile
    private var logFile: File? = null

    private lateinit var jsonStubProxy: Proxy<JSONObject>


    override fun onCreate() {
        super.onCreate()

        configureLoggers()

        jsonStubProxy = JsonStubProxy()
    }

    private fun configureLoggers() {
        logFileName = "${packageName}.log"

        val fileLogger = FileLogger(getLogFile(true))
        val loggers: LoggerAggregator = LoggerAggregator()
            .add(LogcatLogger())
            .add(fileLogger)
        SLF4JLoggerAdapter.setAndroidLogger(loggers)
    }

    fun getLogFile(isErasingAllow: Boolean): File? {
        logFile?.let {
            return it
        }

        val logDir = cacheDir
        val newLogFile = File(logDir, logFileName)
        try {
            if (newLogFile.exists() && newLogFile.isFile) {
                if (isErasingAllow && newLogFile.length() >= LOG_FILE_MAX_SIZE_BYTE) {
                    newLogFile.delete()
                    newLogFile.createNewFile()
                }
            } else {
                newLogFile.createNewFile()
            }
            logFile = newLogFile
        } catch (e: IOException) {
            LOGGER.error("Failed get log file!", e)
        }
        return logFile
    }

    fun getJsonProxy(): Proxy<JSONObject> {
        return jsonStubProxy
    }
}