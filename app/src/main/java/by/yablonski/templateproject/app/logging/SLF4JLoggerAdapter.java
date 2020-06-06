package by.yablonski.templateproject.app.logging;

import org.slf4j.helpers.MarkerIgnoringBase;

/**
 * Class to adapt SLF4J-interface to {@link AndroidLogger}
 *
 * todo: finish adapter implementation
 *
 * @author Andrey Yablonsky on 31.01.2020
 */
public class SLF4JLoggerAdapter extends MarkerIgnoringBase {

    private static AndroidLogger androidLogger = new EmptyAndroidLogger();

    public static void setAndroidLogger(AndroidLogger androidLogger) {
        SLF4JLoggerAdapter.androidLogger = androidLogger;
    }

    public SLF4JLoggerAdapter(final String name) {
        this.name = name;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void trace(String msg) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void trace(String format, Object arg) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void trace(String format, Object... arguments) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void trace(String msg, Throwable t) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String msg) {
        androidLogger.log(LogLevel.DEBUG, new LogEvent(name, msg));
    }

    @Override
    public void debug(String format, Object arg) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void debug(String format, Object... arguments) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void debug(String msg, Throwable t) {
        androidLogger.log(LogLevel.DEBUG, new LogEvent(name, msg, t));
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(String msg) {
        androidLogger.log(LogLevel.INFO, new LogEvent(name, msg));
    }

    @Override
    public void info(String format, Object arg) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void info(String format, Object... arguments) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void info(String msg, Throwable t) {
        androidLogger.log(LogLevel.INFO, new LogEvent(name, msg, t));
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String msg) {
        androidLogger.log(LogLevel.WARN, new LogEvent(name, msg));
    }

    @Override
    public void warn(String format, Object arg) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void warn(String format, Object... arguments) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void warn(String msg, Throwable t) {
        androidLogger.log(LogLevel.WARN, new LogEvent(name, msg, t));
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(String msg) {
        androidLogger.log(LogLevel.ERROR, new LogEvent(name, msg));
    }

    @Override
    public void error(String format, Object arg) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void error(String format, Object... arguments) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void error(String msg, Throwable t) {
        androidLogger.log(LogLevel.ERROR, new LogEvent(name, msg, t));
    }

    private static final class EmptyAndroidLogger implements AndroidLogger {

        @Override
        public void log(LogLevel level, LogEvent event) {
            // todo добавить логирование с использование стандартного логера
        }
    }
}
