package by.yablonski.templateproject.app.logging;

/**
 * App's loggers interface
 *
 * @author Andrey Yablonsky on 31.01.2020
 */
public interface AndroidLogger {

    void log(LogLevel level, LogEvent event);

}
