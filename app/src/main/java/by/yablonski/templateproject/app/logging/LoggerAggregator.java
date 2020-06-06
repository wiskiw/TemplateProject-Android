package by.yablonski.templateproject.app.logging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Compose several loggers to single {@link AndroidLogger} interface. Invoke interface's methods on each delegate.
 *
 * @author Andrey Yablonsky on 31.01.2020
 */
public class LoggerAggregator implements AndroidLogger {

    private final List<AndroidLogger> loggers = new ArrayList<>();

    public LoggerAggregator() {
    }

    public LoggerAggregator(AndroidLogger... loggers) {
        this.loggers.addAll(Arrays.asList(loggers));
    }


    public LoggerAggregator add(AndroidLogger logger) {
        loggers.add(logger);
        return this;
    }

    @Override
    public void log(LogLevel level, LogEvent event) {
        for (AndroidLogger logger : loggers) {
            logger.log(level, event);
        }
    }
}
